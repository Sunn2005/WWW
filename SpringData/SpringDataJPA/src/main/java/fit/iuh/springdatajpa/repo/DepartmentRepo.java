package fit.iuh.springdatajpa.repo;

import fit.iuh.springdatajpa.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DepartmentRepo extends JpaRepository<Department, Integer> {
    @Query("SELECT d FROM Department d WHERE d.id NOT IN (SELECT DISTINCT e.department.id FROM Employee e)")
    List<Department> findDepartmentsWithoutEmployees();
}
