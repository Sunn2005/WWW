package com.example.eshop.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.context.NullSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextHolderFilter;
import org.springframework.security.web.session.DisableEncodeUrlFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity                       // bật @PreAuthorize
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /** Spring Boot tự ráp DaoAuthenticationProvider từ UserDetailsService + PasswordEncoder. */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    private JwtAuthenticationFilter jwtFilter() {
        return new JwtAuthenticationFilter(jwtService, userDetailsService);
    }

    // ================= CHAIN 1: REST API (/api/**) =================
    @Bean
    @Order(1)
    public SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/api/**")
                .csrf(AbstractHttpConfigurer::disable)                 // API dùng header Bearer, không dùng cookie
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .anonymous(a -> a.authorities("ROLE_GUEST"))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/api/ai/chat").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/products/**").permitAll()
                        .requestMatchers("/api/orders/**").hasRole("CUSTOMER")
                        .anyRequest().hasRole("SELLER"))
                .exceptionHandling(e -> e
                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))  // 401
                .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    // ================= CHAIN 2: WEB (Thymeleaf) =================
    @Bean
    @Order(2)
    public SecurityFilterChain webFilterChain(HttpSecurity http) throws Exception {
        http
                // Form Thymeleaf gửi _csrf, đối chiếu với cookie HttpOnly XSRF-TOKEN.
                .csrf(csrf -> csrf.csrfTokenRepository(new CookieCsrfTokenRepository()))
                // JWT được xác thực lại mỗi request. Không dùng SessionManagementFilter:
                // nó coi mỗi request là đăng nhập mới và xoá CSRF, kể cả khi tải CSS/ảnh.
                // SecurityContext không lưu vào session; giỏ hàng vẫn dùng HttpSession riêng.
                .sessionManagement(AbstractHttpConfigurer::disable)
                // SessionManagementConfigurer cũng từng thêm filter này. Giữ lại để
                // session giỏ hàng dùng cookie, không bị ghi ;jsessionid vào link/redirect.
                .addFilterBefore(new DisableEncodeUrlFilter(), SecurityContextHolderFilter.class)
                .securityContext(s -> s.securityContextRepository(new NullSecurityContextRepository()))
                .requestCache(AbstractHttpConfigurer::disable)
                // Người chưa đăng nhập mang quyền ROLE_GUEST
                .anonymous(a -> a.authorities("ROLE_GUEST"))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/css/**", "/js/**", "/img/**", "/favicon.ico", "/error", "/403").permitAll()
                        .requestMatchers("/", "/products", "/products/**", "/login").permitAll()
                        .requestMatchers("/register").hasRole("GUEST")
                        .requestMatchers("/admin/**").hasRole("SELLER")
                        .requestMatchers("/cart/**", "/checkout/**", "/orders/**").hasRole("CUSTOMER")
                        .anyRequest().authenticated())
                .exceptionHandling(e -> e
                        .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login"))  // chưa login → /login
                        .accessDeniedPage("/403"))                                                  // thiếu quyền → /403
                .logout(l -> l
                        .logoutUrl("/logout")                                  // POST /logout (có CSRF)
                        .deleteCookies(JwtService.COOKIE_NAME, "JSESSIONID")
                        .invalidateHttpSession(true)                           // xoá luôn giỏ hàng
                        .logoutSuccessUrl("/?logout"))
                .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
