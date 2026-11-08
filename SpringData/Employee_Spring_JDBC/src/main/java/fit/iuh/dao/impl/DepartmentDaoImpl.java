package fit.iuh.dao.impl;


import java.util.List;

import fit.iuh.dao.DepartmentDao;
import fit.iuh.dto.DepartmentStats;
import fit.iuh.model.Department;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DepartmentDaoImpl implements DepartmentDao {

    private final JdbcTemplate jdbcTemplate;

    public DepartmentDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Department> findAll() {
        return jdbcTemplate.query("SELECT id, name FROM departments ORDER BY id",
            (rs, rowNum) -> new Department(rs.getInt("id"), rs.getString("name")));
    }

    @Override
    public List<DepartmentStats> getStats() {
        return jdbcTemplate.query(
            "SELECT d.id AS dept_id, d.name AS dept_name, COUNT(e.id) AS cnt, " +
            "AVG(e.salary) AS avg_sal, SUM(e.salary) AS total_sal " +
            "FROM departments d LEFT JOIN employees e ON e.department_id = d.id " +
            "GROUP BY d.id, d.name ORDER BY d.id",
            (rs, rowNum) -> new DepartmentStats(
                rs.getInt("dept_id"), rs.getString("dept_name"),
                rs.getLong("cnt"),
                rs.getObject("avg_sal", Double.class),
                rs.getObject("total_sal", Double.class)));
    }

    @Override
    public List<Department> findEmptyDepartments() {
        return jdbcTemplate.query(
            "SELECT d.id, d.name FROM departments d LEFT JOIN employees e ON e.department_id = d.id WHERE e.id IS NULL",
            (rs, rowNum) -> new Department(rs.getInt("id"), rs.getString("name")));
    }
}
