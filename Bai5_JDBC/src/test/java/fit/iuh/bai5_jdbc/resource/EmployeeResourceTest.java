package fit.iuh.bai5_jdbc.resource;

import fit.iuh.bai5_jdbc.model.Employee;
import fit.iuh.bai5_jdbc.repo.EmployeeRepository;
import fit.iuh.bai5_jdbc.service.EmployeeService;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Statement;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EmployeeResourceTest {
    private EmployeeResource resource;

    @BeforeEach
    void setUp() throws Exception {
        JdbcDataSource ds = new JdbcDataSource();
        ds.setURL("jdbc:h2:mem:resource;MODE=MariaDB;DB_CLOSE_DELAY=-1");
        try (Connection con = ds.getConnection(); Statement st = con.createStatement()) {
            st.execute("DROP ALL OBJECTS");
            st.execute("CREATE TABLE departments(id INT PRIMARY KEY, name VARCHAR(100) NOT NULL)");
            st.execute("CREATE TABLE employees(id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(100), " +
                    "role VARCHAR(100), salary DECIMAL(12,2), department_id INT)");
            st.execute("INSERT INTO departments VALUES (1, 'Develop')");
        }
        resource = new EmployeeResource(new EmployeeService(new EmployeeRepository(ds)));
    }

    @Test
    void returnsExpectedCrudStatuses() {
        Employee employee = new Employee(0, "An", "Dev", new BigDecimal("1000"), 1);
        assertEquals(201, resource.create(employee).getStatus());
        assertEquals(200, resource.findById(employee.getId()).getStatus());
        assertEquals(204, resource.delete(employee.getId()).getStatus());
        assertEquals(404, resource.findById(employee.getId()).getStatus());
    }

    @Test
    void returnsBadRequestForInvalidData() {
        Employee employee = new Employee(0, "", "Dev", new BigDecimal("1000"), 1);
        assertEquals(400, resource.create(employee).getStatus());
    }

    @Test
    void injectedConstructorKeepsServiceInitialized() throws Exception {
        Field field = EmployeeResource.class.getDeclaredField("service");
        field.setAccessible(true);
        assertEquals(EmployeeService.class, field.get(resource).getClass());
    }
}
