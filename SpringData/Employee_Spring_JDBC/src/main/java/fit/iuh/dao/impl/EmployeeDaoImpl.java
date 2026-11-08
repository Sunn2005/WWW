package fit.iuh.dao.impl;


import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.NoSuchElementException;

import fit.iuh.dao.EmployeeDao;
import fit.iuh.dto.EmployeeSearchCriteria;
import fit.iuh.model.Employee;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class EmployeeDaoImpl implements EmployeeDao {

    private final JdbcTemplate jdbcTemplate;

    public EmployeeDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String SQL_BASE =
        "SELECT e.id, e.name, e.role, e.age, e.salary, e.department_id, e.position_id, " +
        "d.name AS department_name, p.title AS position_title " +
        "FROM employees e " +
        "LEFT JOIN departments d ON e.department_id = d.id " +
        "LEFT JOIN positions p ON e.position_id = p.id ";

    private final RowMapper<Employee> rowMapper = (rs, rowNum) -> {
        Employee e = new Employee();
        e.setId(rs.getInt("id"));
        e.setName(rs.getString("name"));
        e.setRole(rs.getString("role"));
        e.setAge(rs.getObject("age", Integer.class));
        e.setSalary(rs.getObject("salary", Double.class));
        e.setDepartmentId(rs.getObject("department_id", Integer.class));
        e.setPositionId(rs.getObject("position_id", Integer.class));
        e.setDepartmentName(rs.getString("department_name"));
        e.setPositionTitle(rs.getString("position_title"));
        return e;
    };

    @Override
    public List<Employee> findAll() {
        return jdbcTemplate.query(SQL_BASE + "ORDER BY e.id", rowMapper);
    }

    @Override
    public Optional<Employee> findById(Integer id) {
        List<Employee> list = jdbcTemplate.query(SQL_BASE + "WHERE e.id = ?", rowMapper, id);
        return list.stream().findFirst();
    }

    @Override
    public Employee save(Employee e) {
        if (e.getId() == null) {
            KeyHolder kh = new GeneratedKeyHolder();
            jdbcTemplate.update(con -> {
                PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO employees(name, role, age, salary, department_id, position_id) VALUES(?,?,?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, e.getName());
                ps.setString(2, e.getRole());
                ps.setObject(3, e.getAge());
                ps.setObject(4, e.getSalary());
                ps.setObject(5, e.getDepartmentId());
                ps.setObject(6, e.getPositionId());
                return ps;
            }, kh);
            Number key = kh.getKey();
            if (key != null) e.setId(key.intValue());
            return e;
        }
        int affected = jdbcTemplate.update(
            "UPDATE employees SET name=?, role=?, age=?, salary=?, department_id=?, position_id=? WHERE id=?",
            e.getName(), e.getRole(), e.getAge(), e.getSalary(),
            e.getDepartmentId(), e.getPositionId(), e.getId());
        if (affected == 0) throw new NoSuchElementException("Không tìm thấy nhân viên id=" + e.getId());
        return e;
    }

    @Override
    public void deleteById(Integer id) {
        int affected = jdbcTemplate.update("DELETE FROM employees WHERE id = ?", id);
        if (affected == 0) throw new NoSuchElementException("Không tìm thấy nhân viên id=" + id);
    }

    @Override
    public List<Employee> search(EmployeeSearchCriteria c) {
        StringBuilder sql = new StringBuilder(SQL_BASE + "WHERE 1=1 ");
        List<Object> params = new ArrayList<>();
        if (c.getId() != null) { sql.append("AND e.id = ? "); params.add(c.getId()); }
        if (c.getName() != null && !c.getName().isBlank()) { sql.append("AND e.name LIKE ? "); params.add("%" + c.getName() + "%"); }
        if (c.getMinAge() != null) { sql.append("AND e.age >= ? "); params.add(c.getMinAge()); }
        if (c.getMaxAge() != null) { sql.append("AND e.age <= ? "); params.add(c.getMaxAge()); }
        if (c.getMinSalary() != null) { sql.append("AND e.salary > ? "); params.add(c.getMinSalary()); }
        if (c.getPositionId() != null) { sql.append("AND e.position_id = ? "); params.add(c.getPositionId()); }
        sql.append("ORDER BY e.id");
        return jdbcTemplate.query(sql.toString(), rowMapper, params.toArray());
    }

    @Override
    public List<Employee> findByPositionId(Integer positionId) {
        return jdbcTemplate.query(SQL_BASE + "WHERE e.position_id = ? ORDER BY e.id", rowMapper, positionId);
    }

    @Override
    public List<Employee> findByDepartmentIdAndPositionId(Integer deptId, Integer posId) {
        return jdbcTemplate.query(SQL_BASE + "WHERE e.department_id = ? AND e.position_id = ? ORDER BY e.id",
            rowMapper, deptId, posId);
    }

    @Override
    public List<Employee> findTopNByDepartmentIdOrderBySalaryDesc(Integer deptId, int n) {
        return jdbcTemplate.query(SQL_BASE + "WHERE e.department_id = ? ORDER BY e.salary DESC LIMIT ?",
            rowMapper, deptId, n);
    }
}
