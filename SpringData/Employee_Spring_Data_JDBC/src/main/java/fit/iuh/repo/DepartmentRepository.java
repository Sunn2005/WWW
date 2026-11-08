package fit.iuh.repo;

import java.util.List;

import fit.iuh.dto.DepartmentStats;
import fit.iuh.model.Department;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

public interface DepartmentRepository extends CrudRepository<Department, Integer> {

    @Query("SELECT d.id AS departmentId, d.name AS departmentName, " +
        "COUNT(e.id) AS employeeCount, AVG(e.salary) AS avgSalary, SUM(e.salary) AS totalSalary " +
        "FROM departments d LEFT JOIN employees e ON e.department_id = d.id " +
        "GROUP BY d.id, d.name ORDER BY d.id")
    List<DepartmentStats> getStats();

    @Query("SELECT d.id, d.name FROM departments d " +
        "LEFT JOIN employees e ON e.department_id = d.id WHERE e.id IS NULL")
    List<Department> findEmptyDepartments();
}
