package fit.iuh.service;


import java.util.List;
import java.util.NoSuchElementException;

import fit.iuh.dao.DepartmentDao;
import fit.iuh.dao.EmployeeDao;
import fit.iuh.dao.PositionDao;
import fit.iuh.dto.DepartmentStats;
import fit.iuh.dto.EmployeeForm;
import fit.iuh.dto.EmployeeSearchCriteria;
import fit.iuh.model.Department;
import fit.iuh.model.Employee;
import fit.iuh.model.Position;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    private final EmployeeDao employeeDao;
    private final DepartmentDao departmentDao;
    private final PositionDao positionDao;

    public EmployeeService(EmployeeDao employeeDao, DepartmentDao departmentDao, PositionDao positionDao) {
        this.employeeDao = employeeDao;
        this.departmentDao = departmentDao;
        this.positionDao = positionDao;
    }

    public List<Employee> findAll() { return employeeDao.findAll(); }

    public Employee findById(Integer id) {
        return employeeDao.findById(id)
            .orElseThrow(() -> new NoSuchElementException("Không tìm thấy nhân viên id=" + id));
    }

    public List<Employee> search(EmployeeSearchCriteria c) { return employeeDao.search(c); }

    public Employee save(Integer id, EmployeeForm form) {
        Employee e = new Employee();
        if (id != null) e.setId(id);
        e.setName(form.getName());
        e.setRole(form.getRole());
        e.setAge(form.getAge());
        e.setSalary(form.getSalary());
        e.setDepartmentId(form.getDepartmentId());
        e.setPositionId(form.getPositionId());
        return employeeDao.save(e);
    }

    public void delete(Integer id) { employeeDao.deleteById(id); }

    public List<Department> findAllDepartments() { return departmentDao.findAll(); }
    public List<Position> findAllPositions() { return positionDao.findAll(); }
    public List<Employee> findByPositionId(Integer posId) { return employeeDao.findByPositionId(posId); }
    public List<Employee> findByDepartmentIdAndPositionId(Integer d, Integer p) { return employeeDao.findByDepartmentIdAndPositionId(d, p); }
    public List<DepartmentStats> getDepartmentStats() { return departmentDao.getStats(); }
    public List<Employee> findTopN(Integer deptId, int n) { return employeeDao.findTopNByDepartmentIdOrderBySalaryDesc(deptId, n); }
    public List<Department> findEmptyDepartments() { return departmentDao.findEmptyDepartments(); }
}
