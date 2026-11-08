package fit.iuh.bai5_jdbc.repo;

import fit.iuh.bai5_jdbc.dto.DepartmentSalary;
import fit.iuh.bai5_jdbc.model.Employee;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EmployeeRepository {
    private final DataSource dataSource;

    public EmployeeRepository() {
        this.dataSource = lookupDataSource();
    }

    public EmployeeRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private DataSource lookupDataSource() {
        try {
            return (DataSource) new InitialContext().lookup("java:comp/env/jdbc/hrdb");
        } catch (Exception e) {
            throw new IllegalStateException("Khong tim thay DataSource jdbc/hrdb", e);
        }
    }

    public List<Employee> findAll() throws SQLException {
        String sql = "SELECT id, name, role, salary, department_id FROM employees ORDER BY id";
        List<Employee> employees = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) employees.add(mapEmployee(rs));
        }
        return employees;
    }

    public Optional<Employee> findById(int id) throws SQLException {
        String sql = "SELECT id, name, role, salary, department_id FROM employees WHERE id = ?";
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(mapEmployee(rs)) : Optional.empty();
            }
        }
    }

    public Employee insert(Employee employee) throws SQLException {
        String sql = "INSERT INTO employees(name, role, salary, department_id) VALUES (?, ?, ?, ?)";
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            setValues(ps, employee);
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) employee.setId(keys.getInt(1));
            }
        }
        return employee;
    }

    public boolean update(Employee employee) throws SQLException {
        String sql = "UPDATE employees SET name = ?, role = ?, salary = ?, department_id = ? WHERE id = ?";
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            setValues(ps, employee);
            ps.setInt(5, employee.getId());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean delete(int id) throws SQLException {
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement("DELETE FROM employees WHERE id = ?")) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    public boolean departmentExists(int id) throws SQLException {
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT 1 FROM departments WHERE id = ?")) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    public List<DepartmentSalary> averageSalaryByDepartment() throws SQLException {
        String sql = "SELECT d.id, d.name, AVG(e.salary) average_salary " +
                "FROM departments d LEFT JOIN employees e ON d.id = e.department_id " +
                "GROUP BY d.id, d.name ORDER BY d.id";
        List<DepartmentSalary> result = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                result.add(new DepartmentSalary(rs.getInt("id"), rs.getString("name"),
                        rs.getBigDecimal("average_salary")));
            }
        }
        return result;
    }

    public List<Employee> highestPaidByDepartment() throws SQLException {
        String sql = "SELECT e.id, e.name, e.role, e.salary, e.department_id FROM employees e " +
                "WHERE e.salary = (SELECT MAX(e2.salary) FROM employees e2 " +
                "WHERE e2.department_id = e.department_id) ORDER BY e.department_id, e.id";
        List<Employee> result = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) result.add(mapEmployee(rs));
        }
        return result;
    }

    private void setValues(PreparedStatement ps, Employee employee) throws SQLException {
        ps.setString(1, employee.getName());
        ps.setString(2, employee.getRole());
        ps.setBigDecimal(3, employee.getSalary());
        ps.setInt(4, employee.getDepartmentId());
    }

    private Employee mapEmployee(ResultSet rs) throws SQLException {
        return new Employee(rs.getInt("id"), rs.getString("name"), rs.getString("role"),
                rs.getBigDecimal("salary"), rs.getInt("department_id"));
    }
}
