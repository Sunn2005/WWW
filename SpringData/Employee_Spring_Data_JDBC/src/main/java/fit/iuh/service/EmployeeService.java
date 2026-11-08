package fit.iuh.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import fit.iuh.dto.DepartmentStats;
import fit.iuh.dto.EmployeeForm;
import fit.iuh.dto.EmployeeSearchCriteria;
import fit.iuh.model.Department;
import fit.iuh.model.Employee;
import fit.iuh.model.Position;
import fit.iuh.repo.DepartmentRepository;
import fit.iuh.repo.EmployeeRepository;
import fit.iuh.repo.PositionRepository;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepo;
    private final DepartmentRepository departmentRepo;
    private final PositionRepository positionRepo;

    public EmployeeService(EmployeeRepository employeeRepo,
                           DepartmentRepository departmentRepo,
                           PositionRepository positionRepo) {
        this.employeeRepo = employeeRepo;
        this.departmentRepo = departmentRepo;
        this.positionRepo = positionRepo;
    }

    private void enrichList(List<Employee> list) {
        Map<Integer, String> deptMap = new HashMap<>();
        departmentRepo.findAll().forEach(d -> deptMap.put(d.getId(), d.getName()));
        Map<Integer, String> posMap = new HashMap<>();
        positionRepo.findAll().forEach(p -> posMap.put(p.getId(), p.getTitle()));
        for (Employee e : list) {
            e.setDepartmentName(deptMap.get(e.getDepartmentId()));
            e.setPositionTitle(posMap.get(e.getPositionId()));
        }
    }

    public List<Employee> findAll() {
        List<Employee> list = new ArrayList<>();
        employeeRepo.findAll().forEach(list::add);
        enrichList(list);
        return list;
    }

    public Employee findById(Integer id) {
        Employee e = employeeRepo.findById(id)
            .orElseThrow(() -> new NoSuchElementException("Không tìm thấy nhân viên id=" + id));
        enrichList(List.of(e));
        return e;
    }

    public List<Employee> search(EmployeeSearchCriteria c) {
        String name = (c.getName() == null || c.getName().isBlank()) ? null : c.getName();
        List<Employee> list = employeeRepo.search(
            c.getId(), name, c.getMinAge(), c.getMaxAge(), c.getMinSalary(), c.getPositionId());
        enrichList(list);
        return list;
    }

    public Employee save(Integer id, EmployeeForm form) {
        Employee e = new Employee();
        if (id != null) e.setId(id);
        e.setName(form.getName());
        e.setRole(form.getRole());
        e.setAge(form.getAge());
        e.setSalary(form.getSalary());
        e.setDepartmentId(form.getDepartmentId());
        e.setPositionId(form.getPositionId());
        return employeeRepo.save(e);
    }

    public EmployeeForm getForm(Integer id) {
        Employee e = findById(id);
        EmployeeForm f = new EmployeeForm();
        f.setId(e.getId());
        f.setName(e.getName());
        f.setRole(e.getRole());
        f.setAge(e.getAge());
        f.setSalary(e.getSalary());
        f.setDepartmentId(e.getDepartmentId());
        f.setPositionId(e.getPositionId());
        return f;
    }

    public void delete(Integer id) { employeeRepo.deleteById(id); }

    public List<Department> findAllDepartments() {
        List<Department> list = new ArrayList<>();
        departmentRepo.findAll().forEach(list::add);
        return list;
    }

    public List<Position> findAllPositions() {
        List<Position> list = new ArrayList<>();
        positionRepo.findAll().forEach(list::add);
        return list;
    }

    public List<Employee> findByPositionId(Integer posId) {
        List<Employee> list = employeeRepo.findByPositionId(posId);
        enrichList(list);
        return list;
    }

    public List<Employee> findByDepartmentIdAndPositionId(Integer d, Integer p) {
        List<Employee> list = employeeRepo.findByDepartmentIdAndPositionId(d, p);
        enrichList(list);
        return list;
    }

    public List<DepartmentStats> getDepartmentStats() { return departmentRepo.getStats(); }

    public List<Employee> findTopN(Integer deptId, int n) {
        List<Employee> list = employeeRepo.findTopNByDepartmentId(deptId, n);
        enrichList(list);
        return list;
    }

    public List<Department> findEmptyDepartments() { return departmentRepo.findEmptyDepartments(); }
}
