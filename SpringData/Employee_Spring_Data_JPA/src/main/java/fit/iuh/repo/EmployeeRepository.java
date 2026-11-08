package fit.iuh.repo;

import java.util.List;

import fit.iuh.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    List<Employee> findByNameContainingIgnoreCase(String name);
    List<Employee> findByAgeBetween(Integer minAge, Integer maxAge);
    List<Employee> findBySalaryGreaterThan(Double salary);
    List<Employee> findByPositionId(Integer positionId);
    List<Employee> findByDepartmentIdAndPositionId(Integer deptId, Integer posId);
    List<Employee> findTop5ByDepartmentIdOrderBySalaryDesc(Integer deptId);

    // Top N với N tùy ý: JPQL không có LIMIT nên dùng Pageable
    Page<Employee> findByDepartmentId(Integer deptId, Pageable pageable);

    // JPQL dùng tên field Java (departmentId, positionId), không dùng tên cột DB
    @Query("SELECT e FROM Employee e WHERE " +
        "(:id IS NULL OR e.id = :id) " +
        "AND (:name IS NULL OR LOWER(e.name) LIKE LOWER(CONCAT('%', :name, '%'))) " +
        "AND (:minAge IS NULL OR e.age >= :minAge) " +
        "AND (:maxAge IS NULL OR e.age <= :maxAge) " +
        "AND (:minSalary IS NULL OR e.salary > :minSalary) " +
        "AND (:positionId IS NULL OR e.positionId = :positionId) " +
        "ORDER BY e.id")
    List<Employee> search(@Param("id") Integer id,
                          @Param("name") String name,
                          @Param("minAge") Integer minAge,
                          @Param("maxAge") Integer maxAge,
                          @Param("minSalary") Double minSalary,
                          @Param("positionId") Integer positionId);
}
