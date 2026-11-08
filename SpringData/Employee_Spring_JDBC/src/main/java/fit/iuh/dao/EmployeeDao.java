package fit.iuh.dao;


import fit.iuh.dto.EmployeeSearchCriteria;
import fit.iuh.model.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeDao {
    List<Employee> findAll();
    Optional<Employee> findById(Integer id);
    Employee save(Employee e);
    void deleteById(Integer id);
    List<Employee> search(EmployeeSearchCriteria c);
    List<Employee> findByPositionId(Integer positionId);
    List<Employee> findByDepartmentIdAndPositionId(Integer deptId, Integer posId);
    List<Employee> findTopNByDepartmentIdOrderBySalaryDesc(Integer deptId, int n);
}
