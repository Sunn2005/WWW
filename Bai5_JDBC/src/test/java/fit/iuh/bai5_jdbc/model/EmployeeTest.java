package fit.iuh.bai5_jdbc.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EmployeeTest {
    @Test
    void keepsSalaryAndDepartmentWithDatabaseTypes() {
        Employee employee = new Employee(1, "An", "Developer", new BigDecimal("1500.50"), 2);

        assertEquals(new BigDecimal("1500.50"), employee.getSalary());
        assertEquals(2, employee.getDepartmentId());
    }
}
