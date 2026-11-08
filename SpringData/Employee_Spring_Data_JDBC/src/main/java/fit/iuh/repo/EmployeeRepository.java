package fit.iuh.repo;

import java.util.List;

import fit.iuh.model.Employee;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface EmployeeRepository extends CrudRepository<Employee, Integer> {

    // Tìm kiếm đơn, đặt tên là tự sinh query, không cần viết SQL
    List<Employee> findByNameContainingIgnoreCase(String name);
    List<Employee> findByAgeBetween(Integer minAge, Integer maxAge);
    List<Employee> findBySalaryGreaterThan(Double salary);
    List<Employee> findByPositionId(Integer positionId);
    List<Employee> findByDepartmentIdAndPositionId(Integer deptId, Integer posId);
    List<Employee> findTop5ByDepartmentIdOrderBySalaryDesc(Integer deptId);

    // Top N với N tùy ý
    @Query("SELECT * FROM employees WHERE department_id = :deptId ORDER BY salary DESC LIMIT :n")
    List<Employee> findTopNByDepartmentId(@Param("deptId") Integer deptId, @Param("n") int n);

    // Tìm kiếm tổng hợp, cái nào null thì bỏ qua điều kiện đó
    @Query("SELECT * FROM employees e WHERE " +
        "(:id IS NULL OR e.id = :id) " +
        "AND (:name IS NULL OR e.name LIKE CONCAT('%', :name, '%')) " +
        "AND (:minAge IS NULL OR e.age >= :minAge) " +
        "AND (:maxAge IS NULL OR e.age <= :maxAge) " +
        "AND (:minSalary IS NULL OR e.salary > :minSalary) " +
        "AND (:positionId IS NULL OR e.position_id = :positionId) " +
        "ORDER BY e.id")
    List<Employee> search(@Param("id") Integer id,
                          @Param("name") String name,
                          @Param("minAge") Integer minAge,
                          @Param("maxAge") Integer maxAge,
                          @Param("minSalary") Double minSalary,
                          @Param("positionId") Integer positionId);
}
