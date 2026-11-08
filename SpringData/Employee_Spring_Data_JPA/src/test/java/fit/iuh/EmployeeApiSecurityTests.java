package fit.iuh;

import fit.iuh.config.SecurityConfig;
import fit.iuh.controller.EmployeeRestController;
import fit.iuh.model.Employee;
import fit.iuh.repo.UserRepository;
import fit.iuh.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@SpringJUnitWebConfig(EmployeeApiSecurityTests.TestConfig.class)
class EmployeeApiSecurityTests {
    @Configuration
    @EnableWebMvc
    @Import({SecurityConfig.class, EmployeeRestController.class})
    static class TestConfig {
        @Bean
        EmployeeService employeeService() { return mock(EmployeeService.class); }

        @Bean
        UserRepository userRepository() { return mock(UserRepository.class); }
    }

    @Autowired WebApplicationContext context;
    @Autowired EmployeeService service;
    MockMvc mvc;

    @BeforeEach
    void setup() {
        reset(service);
        when(service.save(nullable(Integer.class), any())).thenReturn(new Employee());
        when(service.findById(anyInt())).thenReturn(new Employee());
        mvc = webAppContextSetup(context).apply(springSecurity()).build();
    }

    @ParameterizedTest
    @CsvSource({
        "GUEST,POST,/api/employees,403", "USER,POST,/api/employees,201", "ADMIN,POST,/api/employees,201",
        "GUEST,PUT,/api/employees/1,403", "USER,PUT,/api/employees/1,200", "ADMIN,PUT,/api/employees/1,200",
        "GUEST,DELETE,/api/employees/1,403", "USER,DELETE,/api/employees/1,403", "ADMIN,DELETE,/api/employees/1,204"
    })
    void mutationPermissions(String role, String method, String path, int expected) throws Exception {
        mvc.perform(request(HttpMethod.valueOf(method), path).with(user("tester").roles(role))
                .with(csrf()).contentType(MediaType.APPLICATION_JSON).content("{\"name\":\"Test Employee\"}"))
                .andExpect(status().is(expected));
        if (expected == 403) verifyNoInteractions(service);
    }

    @ParameterizedTest
    @CsvSource({"GUEST,403", "USER,200", "ADMIN,200"})
    void salarySearchPermissions(String role, int expected) throws Exception {
        mvc.perform(get("/api/employees/search").param("minSalary", "1000")
                .with(user("tester").roles(role))).andExpect(status().is(expected));
        if (expected == 403) verifyNoInteractions(service);
        else verify(service).search(argThat(criteria -> Double.valueOf(1000).equals(criteria.getMinSalary())));
    }

    @ParameterizedTest
    @ValueSource(strings = {"GUEST", "USER", "ADMIN"})
    void allRolesCanReadAndSearchWithoutSalary(String role) throws Exception {
        mvc.perform(get("/api/employees").with(user("tester").roles(role))).andExpect(status().isOk());
        mvc.perform(get("/api/employees/1").with(user("tester").roles(role))).andExpect(status().isOk());
        mvc.perform(get("/api/employees/search").param("name", "Test").param("positionId", "1")
                .with(user("tester").roles(role))).andExpect(status().isOk());
    }

    @ParameterizedTest
    @ValueSource(strings = {"/api/employees", "/api/employees/1", "/api/employees/search"})
    void anonymousCannotRead(String path) throws Exception {
        mvc.perform(get(path)).andExpect(status().is3xxRedirection());
        verifyNoInteractions(service);
    }

    @ParameterizedTest
    @ValueSource(strings = {"/api/employees", "/api/employees/1", "/api/employees/search"})
    void unknownRoleCannotRead(String path) throws Exception {
        mvc.perform(get(path).with(user("tester").roles("OTHER"))).andExpect(status().isForbidden());
        verifyNoInteractions(service);
    }

    @ParameterizedTest
    @ValueSource(strings = {"POST", "PUT", "DELETE"})
    void mutationsStillRequireCsrf(String method) throws Exception {
        String path = method.equals("POST") ? "/api/employees" : "/api/employees/1";
        mvc.perform(request(HttpMethod.valueOf(method), path).with(user("tester").roles("ADMIN"))
                .contentType(MediaType.APPLICATION_JSON).content("{\"name\":\"Test Employee\"}"))
                .andExpect(status().isForbidden());
        verifyNoInteractions(service);
    }
}
