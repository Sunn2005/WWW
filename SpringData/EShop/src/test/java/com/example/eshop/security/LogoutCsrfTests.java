package com.example.eshop.security;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@SpringJUnitConfig(LogoutCsrfTests.Config.class)
@WebAppConfiguration
class LogoutCsrfTests {
    private MockMvc mvc;
    private FilterChainProxy securityFilterChain;

    @BeforeEach
    void setup(WebApplicationContext context) {
        when(context.getBean(JwtService.class).extractUsername("test-jwt")).thenReturn("customer1");
        when(context.getBean(CustomUserDetailsService.class).loadUserByUsername("customer1"))
                .thenReturn(User.withUsername("customer1").password("unused").roles("CUSTOMER").build());
        mvc = webAppContextSetup(context).apply(springSecurity()).build();
        securityFilterChain = context.getBean(FilterChainProxy.class);
    }

    @Test
    void cartLinksAndRedirectsNeverExposeSessionIdInUrl() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/cart");
        request.setServletPath("/cart");
        request.setCookies(new Cookie(JwtService.COOKIE_NAME, "test-jwt"));
        // Mô phỏng container ghi session vào URL khi trình duyệt chưa có JSESSIONID.
        MockHttpServletResponse response = new MockHttpServletResponse() {
            @Override
            public String encodeURL(String url) { return url + ";jsessionid=test-session"; }
            @Override
            public String encodeRedirectURL(String url) { return url + ";jsessionid=test-session"; }
        };
        securityFilterChain.doFilter(request, response, (req, res) -> {
            var httpResponse = (HttpServletResponse) res;
            httpResponse.getWriter().write(httpResponse.encodeURL("/cart") + "|"
                    + httpResponse.encodeRedirectURL("/cart"));
        });
        assertEquals(200, response.getStatus());
        assertEquals("/cart|/cart", response.getContentAsString());
    }

    @Test
    void authenticatedAssetsDoNotDeleteCsrfCookieBeforeLogoutOrLogin() throws Exception {
        Cookie jwt = new Cookie(JwtService.COOKIE_NAME, "test-jwt");
        MvcResult page = mvc.perform(get("/login").cookie(jwt))
                .andExpect(status().isOk()).andReturn();
        Cookie csrf = page.getResponse().getCookie("XSRF-TOKEN");
        assertNotNull(csrf);
        String token = page.getResponse().getContentAsString();

        // Trình duyệt tải CSS/ảnh sau HTML, trước khi người dùng gửi form.
        for (String asset : new String[]{"/css/style.css", "/img/test.png"}) {
            mvc.perform(get(asset).cookie(jwt, csrf))
                    .andExpect(status().isOk())
                    .andExpect(cookie().doesNotExist("XSRF-TOKEN"));
        }
        mvc.perform(post("/login").cookie(jwt, csrf).param("_csrf", token))
                .andExpect(status().isOk());
        mvc.perform(post("/logout").cookie(jwt, csrf).param("_csrf", token))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/?logout"))
                .andExpect(cookie().maxAge(JwtService.COOKIE_NAME, 0))
                .andExpect(cookie().maxAge("XSRF-TOKEN", 0));
    }

    @Test
    void validFormTokenAndCookieAllowLogoutWithoutSession() throws Exception {
        MvcResult page = mvc.perform(get("/login")).andExpect(status().isOk()).andReturn();
        Cookie csrfCookie = page.getResponse().getCookie("XSRF-TOKEN");
        assertNotNull(csrfCookie);
        assertTrue(csrfCookie.isHttpOnly());

        // Token lấy qua request attribute giống cách Thymeleaf render form.
        // Không gửi JSESSIONID: CSRF phải hoạt động độc lập với HttpSession.
        mvc.perform(post("/logout").cookie(csrfCookie)
                        .param("_csrf", page.getResponse().getContentAsString()))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/?logout"))
                .andExpect(cookie().maxAge(JwtService.COOKIE_NAME, 0))
                .andExpect(cookie().maxAge("JSESSIONID", 0))
                .andExpect(cookie().maxAge("XSRF-TOKEN", 0));
    }

    @Test
    void missingTokenStillRejectsLogout() throws Exception {
        mvc.perform(post("/logout"))
                .andExpect(status().isForbidden())
                .andExpect(forwardedUrl("/403"));
    }

    @Test
    void mismatchedTokenStillRejectsLogout() throws Exception {
        MvcResult page = mvc.perform(get("/login")).andReturn();
        mvc.perform(post("/logout").cookie(page.getResponse().getCookie("XSRF-TOKEN"))
                        .param("_csrf", "invalid-token"))
                .andExpect(status().isForbidden())
                .andExpect(forwardedUrl("/403"));
    }

    @Configuration
    @EnableWebMvc
    @Import(SecurityConfig.class)
    static class Config {
        @Bean
        JwtService jwtService() { return mock(JwtService.class); }

        @Bean
        CustomUserDetailsService userDetailsService() { return mock(CustomUserDetailsService.class); }

        @Bean
        TokenPage tokenPage() { return new TokenPage(); }
    }

    @RestController
    static class TokenPage {
        @GetMapping("/login")
        String login(@RequestAttribute("_csrf") CsrfToken token) {
            return token.getToken();
        }

        @GetMapping({"/css/style.css", "/img/test.png"})
        String asset() { return "asset"; }

        @PostMapping("/login")
        String submitLogin() { return "CSRF accepted"; }
    }
}
