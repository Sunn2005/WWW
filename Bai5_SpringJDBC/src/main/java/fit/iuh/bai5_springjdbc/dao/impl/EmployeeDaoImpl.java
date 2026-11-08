package fit.iuh.bai5_springjdbc.dao.impl;

import fit.iuh.bai5_springjdbc.dao.EmployeeDao;
import fit.iuh.bai5_springjdbc.enitity.Employee;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmployeeDaoImpl implements EmployeeDao {
    private final JdbcTemplate jdbcTemplate;


    public EmployeeDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Employee> rowMapper = ((rs, rowNum) -> new Employee(
            rs.getInt("id"),
            rs.getString("name"),
            rs.getString("role"),
            rs.getDouble("salary")
    ));

    @Override
    public void save(Employee employee) {
        String sql = " INSERT INTO employees\n" +
                "                (name, role, salary)\n" +
                "                VALUES (?, ?, ?)";
        jdbcTemplate.update(
                sql,
                employee.getName(),
                employee.getRole(),
                employee.getSalary()
        );

    }

    @Override
    public void update(Employee employee) {
        String sql = "";
        jdbcTemplate.update(
                sql,
                employee.getName(),
                employee.getRole(),
                employee.getSalary()
        );
    }

    @Override
    public Employee getById(int id) {
        return null;
    }

    @Override
    public List<Employee> getAll() {

        String sql = "SELECT * FROM employees";
        return  jdbcTemplate.query(sql,rowMapper);
    }

    @Override
    public void deleteById(int id) {

    }
}
