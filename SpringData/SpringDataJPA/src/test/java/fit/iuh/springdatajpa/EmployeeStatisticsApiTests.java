package fit.iuh.springdatajpa;

import fit.iuh.springdatajpa.model.Department;
import fit.iuh.springdatajpa.model.Employee;
import fit.iuh.springdatajpa.model.Position;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class EmployeeStatisticsApiTests {
    @Autowired
    private MockMvc mvc;

    @PersistenceContext
    private EntityManager entityManager;

    @BeforeEach
    void setUp() {
        Department department = new Department(1, "IT", null);
        entityManager.persist(department);
        entityManager.persist(new Department(2, "Empty department", null));
        Position position = new Position(1, "Developer", null);
        entityManager.persist(position);
        entityManager.persist(new Position(2, "Empty position", null));
        saveEmployee(1, 100, "2024-02-01", department, position);
        saveEmployee(2, 200, "2024-02-29", department, null);
        saveEmployee(3, 300, "2025-01-01", null, position);
        saveEmployee(4, 400, null, null, null);
        entityManager.flush();
    }

    @Test
    void summaryIncludesEmployeesWithoutDepartmentOrHireDate() throws Exception {
        mvc.perform(get("/api/employees/stats/summary"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.employeeCount").value(4))
                .andExpect(jsonPath("$.averageSalary").value(250.0))
                .andExpect(jsonPath("$.totalSalary").value(1000.0))
                .andExpect(jsonPath("$.minSalary").value(100.0))
                .andExpect(jsonPath("$.maxSalary").value(400.0));
    }

    @Test
    void departmentsIncludeEmptyGroupsAndSupportHavingFilter() throws Exception {
        mvc.perform(get("/api/employees/stats/department-details"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].employeeCount").value(2))
                .andExpect(jsonPath("$[0].averageSalary").value(150.0))
                .andExpect(jsonPath("$[0].totalSalary").value(300.0))
                .andExpect(jsonPath("$[0].minSalary").value(100.0))
                .andExpect(jsonPath("$[0].maxSalary").value(200.0))
                .andExpect(jsonPath("$[1].employeeCount").value(0))
                .andExpect(jsonPath("$[1].totalSalary").value(0.0))
                .andExpect(jsonPath("$[1].averageSalary").isEmpty());
        mvc.perform(get("/api/employees/stats/department-details").param("minEmployees", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    void positionsAggregateTheirOwnEmployees() throws Exception {
        mvc.perform(get("/api/employees/stats/position"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Developer"))
                .andExpect(jsonPath("$[0].employeeCount").value(2))
                .andExpect(jsonPath("$[0].totalSalary").value(400.0))
                .andExpect(jsonPath("$[1].employeeCount").value(0));
    }

    @Test
    void hireStatisticsGroupDatesAndExcludeUnknownDates() throws Exception {
        mvc.perform(get("/api/employees/stats/hire-monthly").param("year", "2024"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].year").value(2024))
                .andExpect(jsonPath("$[0].month").value(2))
                .andExpect(jsonPath("$[0].employeeCount").value(2))
                .andExpect(jsonPath("$[0].totalSalary").value(300.0));
        mvc.perform(get("/api/employees/stats/hire-yearly"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].year").value(2024))
                .andExpect(jsonPath("$[0].employeeCount").value(2))
                .andExpect(jsonPath("$[1].year").value(2025))
                .andExpect(jsonPath("$[1].employeeCount").value(1));
    }

    @Test
    void emptyDatabaseReturnsZeroCountAndNoHireGroups() throws Exception {
        entityManager.createQuery("DELETE FROM Employee").executeUpdate();
        entityManager.clear();
        mvc.perform(get("/api/employees/stats/summary"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.employeeCount").value(0))
                .andExpect(jsonPath("$.totalSalary").value(0.0))
                .andExpect(jsonPath("$.averageSalary").isEmpty())
                .andExpect(jsonPath("$.minSalary").isEmpty())
                .andExpect(jsonPath("$.maxSalary").isEmpty());
        mvc.perform(get("/api/employees/stats/hire-yearly"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void invalidStatisticsParametersReturnBadRequest() throws Exception {
        mvc.perform(get("/api/employees/stats/department-details").param("minEmployees", "-1"))
                .andExpect(status().isBadRequest());
        mvc.perform(get("/api/employees/stats/hire-monthly").param("year", "0"))
                .andExpect(status().isBadRequest());
        mvc.perform(get("/api/employees/stats/hire-monthly"))
                .andExpect(status().isBadRequest());
    }

    private void saveEmployee(int id, double salary, String date, Department department, Position position) {
        Employee employee = new Employee();
        employee.setId(id);
        employee.setName("Employee " + id);
        employee.setSalary(salary);
        employee.setHireDate(date == null ? null : LocalDate.parse(date));
        employee.setDepartment(department);
        employee.setPosition(position);
        entityManager.persist(employee);
    }
}
