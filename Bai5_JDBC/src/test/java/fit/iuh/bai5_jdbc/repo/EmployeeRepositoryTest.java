package fit.iuh.bai5_jdbc.repo;

import fit.iuh.bai5_jdbc.model.Employee;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeRepositoryTest {
    private EmployeeRepository repository;

    @BeforeEach
    void setUp() throws Exception {
        JdbcDataSource ds = new JdbcDataSource();
        ds.setURL("jdbc:h2:mem:hr;MODE=MariaDB;DB_CLOSE_DELAY=-1");
        try (Connection con = ds.getConnection(); Statement st = con.createStatement()) {
            st.execute("DROP ALL OBJECTS");
            st.execute("CREATE TABLE departments(id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(100) NOT NULL)");
            st.execute("CREATE TABLE employees(id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(100) NOT NULL, " +
                    "role VARCHAR(100) NOT NULL, salary DECIMAL(12,2) NOT NULL, department_id INT NOT NULL, " +
                    "FOREIGN KEY(department_id) REFERENCES departments(id))");
            st.execute("INSERT INTO departments(name) VALUES ('Develop'), ('Admin')");
            st.execute("INSERT INTO employees(name, role, salary, department_id) VALUES " +
                    "('An','Dev',1000,1),('Binh','Dev',2000,1),('Chi','Lead',2000,1),('Dung','Admin',3000,2)");
        }
        repository = new EmployeeRepository(ds);
    }

    @Test
    void performsCrud() throws Exception {
        Employee employee = repository.insert(new Employee(0, "Hoa", "Tester", new BigDecimal("1500"), 1));
        assertTrue(employee.getId() > 0);
        assertEquals("Hoa", repository.findById(employee.getId()).orElseThrow().getName());

        employee.setName("Hoa Nguyen");
        assertTrue(repository.update(employee));
        assertEquals("Hoa Nguyen", repository.findById(employee.getId()).orElseThrow().getName());

        assertTrue(repository.delete(employee.getId()));
        assertTrue(repository.findById(employee.getId()).isEmpty());
    }

    @Test
    void calculatesAverageAndReturnsAllHighestPaidTies() throws Exception {
        assertEquals(new BigDecimal("1666.666666666667"),
                repository.averageSalaryByDepartment().get(0).getAverageSalary());
        assertEquals(3, repository.highestPaidByDepartment().size());
    }
}
