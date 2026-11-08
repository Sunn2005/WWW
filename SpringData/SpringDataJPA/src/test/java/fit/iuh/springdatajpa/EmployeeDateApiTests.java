package fit.iuh.springdatajpa;

import fit.iuh.springdatajpa.model.Employee;
import fit.iuh.springdatajpa.repo.EmployeeRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class EmployeeDateApiTests {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private EmployeeRepo employeeRepo;

    @BeforeEach
    void setUp() {
        saveEmployee(1, "2023-12-31");
        saveEmployee(2, "2024-02-01");
        saveEmployee(3, "2024-02-29");
        saveEmployee(4, "2024-12-31");
        saveEmployee(5, "2025-02-01");
        saveEmployee(6, null);
        employeeRepo.flush();
    }

    @Test
    void findsExactDateAndSerializesLocalDate() throws Exception {
        mvc.perform(get("/api/employees/search/hire-date").param("date", "2024-02-29"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].id", containsInAnyOrder(3)))
                .andExpect(jsonPath("$[0].hireDate").value("2024-02-29"));
    }

    @Test
    void beforeAndAfterExcludeTheRequestedDateAndNullDates() throws Exception {
        mvc.perform(get("/api/employees/search/hired-before").param("date", "2024-02-01"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].id", containsInAnyOrder(1)));
        mvc.perform(get("/api/employees/search/hired-after").param("date", "2024-12-31"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].id", containsInAnyOrder(5)));
    }

    @Test
    void rangeIncludesBothEndpoints() throws Exception {
        mvc.perform(get("/api/employees/search/hire-date-between")
                        .param("startDate", "2024-02-01").param("endDate", "2024-02-29"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].id", containsInAnyOrder(2, 3)));
    }

    @Test
    void monthQueryFiltersByBothMonthAndYear() throws Exception {
        mvc.perform(get("/api/employees/search/hire-month").param("month", "2").param("year", "2024"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].id", containsInAnyOrder(2, 3)));
    }

    @Test
    void yearQueryIncludesDecember31AndExcludesOtherYears() throws Exception {
        mvc.perform(get("/api/employees/search/hire-year").param("year", "2024"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].id", containsInAnyOrder(2, 3, 4)));
    }

    @Test
    void rejectsInvalidDatesAndInvalidSearchRanges() throws Exception {
        mvc.perform(get("/api/employees/search/hire-date").param("date", "2023-02-29"))
                .andExpect(status().isBadRequest());
        mvc.perform(get("/api/employees/search/hire-date"))
                .andExpect(status().isBadRequest());
        mvc.perform(get("/api/employees/search/hire-date-between")
                        .param("startDate", "2024-03-01").param("endDate", "2024-02-01"))
                .andExpect(status().isBadRequest());
        mvc.perform(get("/api/employees/search/hire-month").param("month", "13").param("year", "2024"))
                .andExpect(status().isBadRequest());
        mvc.perform(get("/api/employees/search/hire-year").param("year", "0"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createsUpdatesAndReadsHireDateThroughService() throws Exception {
        mvc.perform(post("/api/employees").contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"id":10,"name":"New employee","age":25,"salary":18000000,"hireDate":"2024-02-29"}
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.hireDate").value("2024-02-29"));
        employeeRepo.flush();
        assertThat(employeeRepo.findById(10).orElseThrow().getHireDate()).isEqualTo(LocalDate.of(2024, 2, 29));

        mvc.perform(put("/api/employees/10").contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"id":10,"name":"Updated employee","age":26,"salary":20000000,"hireDate":"2025-01-01"}
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10));
        mvc.perform(get("/api/employees/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.hireDate").value("2025-01-01"));
        mvc.perform(delete("/api/employees/10")).andExpect(status().isOk());
        mvc.perform(get("/api/employees/10")).andExpect(status().isNotFound());
    }

    @Test
    void invalidJsonDateAndMissingEmployeeReturnClientErrors() throws Exception {
        mvc.perform(post("/api/employees").contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"id":10,"age":25,"salary":18000000,"hireDate":"2024-13-01"}
                                """))
                .andExpect(status().isBadRequest());
        mvc.perform(put("/api/employees/99").contentType(MediaType.APPLICATION_JSON).content("{\"id\":99,\"age\":25,\"salary\":18000000}"))
                .andExpect(status().isNotFound());
    }

    private void saveEmployee(int id, String hireDate) {
        Employee employee = new Employee();
        employee.setId(id);
        employee.setName("Employee " + id);
        employee.setHireDate(hireDate == null ? null : LocalDate.parse(hireDate));
        employeeRepo.save(employee);
    }
}
