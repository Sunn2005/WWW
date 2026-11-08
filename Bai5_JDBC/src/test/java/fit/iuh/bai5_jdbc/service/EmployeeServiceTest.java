package fit.iuh.bai5_jdbc.service;

import fit.iuh.bai5_jdbc.model.Employee;
import fit.iuh.bai5_jdbc.repo.EmployeeRepository;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeServiceTest {
    private EmployeeService service;

    @BeforeEach
    void setUp() throws Exception {
        JdbcDataSource ds = new JdbcDataSource();
        ds.setURL("jdbc:h2:mem:service;MODE=MariaDB;DB_CLOSE_DELAY=-1");
        try (Connection con = ds.getConnection(); Statement st = con.createStatement()) {
            st.execute("DROP ALL OBJECTS");
            st.execute("CREATE TABLE departments(id INT PRIMARY KEY, name VARCHAR(100) NOT NULL)");
            st.execute("CREATE TABLE employees(id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(100), " +
                    "role VARCHAR(100), salary DECIMAL(12,2), department_id INT)");
            st.execute("INSERT INTO departments VALUES (1, 'Develop')");
        }
        service = new EmployeeService(new EmployeeRepository(ds));
    }

    @Test
    void rejectsInvalidEmployee() {
        Employee employee = new Employee(0, " ", "Dev", new BigDecimal("1000"), 1);
        assertThrows(IllegalArgumentException.class, () -> service.create(employee));
    }

    @Test
    void rejectsUnknownDepartment() {
        Employee employee = new Employee(0, "An", "Dev", new BigDecimal("1000"), 99);
        assertThrows(IllegalArgumentException.class, () -> service.create(employee));
    }

    @Test
    void usesUrlIdWhenUpdating() throws Exception {
        Employee employee = service.create(new Employee(0, "An", "Dev", new BigDecimal("1000"), 1));
        employee.setId(99);
        employee.setName("An Nguyen");

        assertTrue(service.update(1, employee));
        assertEquals(1, employee.getId());
    }
}
