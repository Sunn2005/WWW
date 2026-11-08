package fit.iuh.bai5_jdbc.service;

import fit.iuh.bai5_jdbc.dto.DepartmentSalary;
import fit.iuh.bai5_jdbc.model.Employee;
import fit.iuh.bai5_jdbc.repo.EmployeeRepository;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class EmployeeService {
    private final EmployeeRepository repository;

    public EmployeeService() {
        this(new EmployeeRepository());
    }

    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }

    public List<Employee> findAll() throws SQLException {
        return repository.findAll();
    }

    public Optional<Employee> findById(int id) throws SQLException {
        return repository.findById(id);
    }

    public Employee create(Employee employee) throws SQLException {
        validate(employee);
        return repository.insert(employee);
    }

    public boolean update(int id, Employee employee) throws SQLException {
        validate(employee);
        employee.setId(id);
        return repository.update(employee);
    }

    public boolean delete(int id) throws SQLException {
        return repository.delete(id);
    }

    public List<DepartmentSalary> averageSalaryByDepartment() throws SQLException {
        return repository.averageSalaryByDepartment();
    }

    public List<Employee> highestPaidByDepartment() throws SQLException {
        return repository.highestPaidByDepartment();
    }

    private void validate(Employee employee) throws SQLException {
        if (employee == null) throw new IllegalArgumentException("Du lieu nhan vien khong hop le");
        if (employee.getName() == null || employee.getName().isBlank())
            throw new IllegalArgumentException("Ten nhan vien khong duoc rong");
        if (employee.getRole() == null || employee.getRole().isBlank())
            throw new IllegalArgumentException("Chuc vu khong duoc rong");
        if (employee.getSalary() == null || employee.getSalary().compareTo(BigDecimal.ZERO) < 0)
            throw new IllegalArgumentException("Luong phai lon hon hoac bang 0");
        if (employee.getDepartmentId() == null || !repository.departmentExists(employee.getDepartmentId()))
            throw new IllegalArgumentException("Phong ban khong ton tai");
    }
}
