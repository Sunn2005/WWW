package fit.iuh.repo;


import java.util.List;

import fit.iuh.dto.DepartmentStats;
import fit.iuh.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {

    // JPQL + constructor expression, phải ghi đủ package của DTO
    @Query("SELECT new fit.iuh.dto.DepartmentStats(" +
        "d.id, d.name, COUNT(e.id), AVG(e.salary), SUM(e.salary)) " +
        "FROM Department d LEFT JOIN Employee e ON e.departmentId = d.id " +
        "GROUP BY d.id, d.name ORDER BY d.id")
    List<DepartmentStats> getStats();

    @Query("SELECT d FROM Department d WHERE d.id NOT IN " +
        "(SELECT DISTINCT e.departmentId FROM Employee e WHERE e.departmentId IS NOT NULL)")
    List<Department> findEmptyDepartments();
}
