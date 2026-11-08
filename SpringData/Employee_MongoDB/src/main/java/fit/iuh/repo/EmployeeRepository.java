package fit.iuh.repo;

import java.util.List;

import fit.iuh.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EmployeeRepository extends MongoRepository<Employee, Integer> {

    List<Employee> findByNameContainingIgnoreCase(String name);
    List<Employee> findByAgeBetween(Integer minAge, Integer maxAge);
    List<Employee> findBySalaryGreaterThan(Double salary);
    List<Employee> findByPositionId(Integer positionId);
    List<Employee> findByDepartmentIdAndPositionId(Integer deptId, Integer posId);
    List<Employee> findTop5ByDepartmentIdOrderBySalaryDesc(Integer deptId);
    Page<Employee> findByDepartmentId(Integer deptId, Pageable pageable);
}
