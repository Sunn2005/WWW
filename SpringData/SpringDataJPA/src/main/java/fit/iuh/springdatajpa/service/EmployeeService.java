package fit.iuh.springdatajpa.service;

import fit.iuh.springdatajpa.model.Employee;
import fit.iuh.springdatajpa.dto.EmployeeSummary;
import fit.iuh.springdatajpa.dto.EmployeeGroupStats;
import fit.iuh.springdatajpa.dto.HireMonthStats;
import fit.iuh.springdatajpa.dto.HireYearStats;
import fit.iuh.springdatajpa.repo.EmployeeRepo;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class EmployeeService {
    private final EmployeeRepo employeeRepo;

    public EmployeeService(EmployeeRepo employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

    public List<Employee> getAll() {
        return employeeRepo.findAll();
    }

    public Employee getById(Integer id) {
        return employeeRepo.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found: " + id));
    }

    @Transactional
    public Employee create(Employee employee) {
        return employeeRepo.save(employee);
    }

    @Transactional
    public Employee update(Integer id, Employee employee) {
        getById(id);
        employee.setId(id);
        return employeeRepo.save(employee);
    }

    @Transactional
    public void delete(Integer id) {
        employeeRepo.delete(getById(id));
    }

    public List<Employee> byName(String name) {
        return employeeRepo.findByNameContainingIgnoreCase(name);
    }

    public List<Employee> byAge(Integer min, Integer max) {
        return employeeRepo.findByAgeBetween(min, max);
    }

    public List<Employee> bySalary(Double min) {
        return employeeRepo.findBySalaryGreaterThan(min);
    }

    public List<Employee> byPosition(Integer positionId) {
        return employeeRepo.findByPositionId(positionId);
    }

    public List<Employee> byDeptAndPosition(Integer departmentId, Integer positionId) {
        return employeeRepo.findByDepartmentIdAndPositionId(departmentId, positionId);
    }

    public List<Object[]> statsByDepartment() {
        return employeeRepo.statsByDepartment();
    }

    public EmployeeSummary summary() {
        return employeeRepo.summarizeEmployees();
    }

    public List<EmployeeGroupStats> departmentStatistics(long minEmployees) {
        if (minEmployees < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "minEmployees must be at least 0");
        }
        return employeeRepo.departmentStatistics(minEmployees);
    }

    public List<EmployeeGroupStats> positionStatistics() {
        return employeeRepo.positionStatistics();
    }

    public List<HireMonthStats> monthlyHireStatistics(int year) {
        validateYear(year);
        return employeeRepo.monthlyHireStatistics(LocalDate.of(year, 1, 1), LocalDate.of(year, 12, 31));
    }

    public List<HireYearStats> yearlyHireStatistics() {
        return employeeRepo.yearlyHireStatistics();
    }

    public List<Employee> topSalary(Integer departmentId, int n) {
        if (n <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "n must be greater than 0");
        }
        return employeeRepo.findByDepartmentIdOrderBySalaryDesc(departmentId, PageRequest.of(0, n));
    }

    public List<Employee> byHireDate(LocalDate date) {
        return employeeRepo.findByHireDate(date);
    }

    public List<Employee> hiredBefore(LocalDate date) {
        return employeeRepo.findByHireDateBefore(date);
    }

    public List<Employee> hiredAfter(LocalDate date) {
        return employeeRepo.findByHireDateAfter(date);
    }

    public List<Employee> hiredBetween(LocalDate startDate, LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "startDate must be on or before endDate");
        }
        return employeeRepo.findByHireDateBetween(startDate, endDate);
    }

    public List<Employee> byHireMonthAndYear(int month, int year) {
        validateYear(year);
        if (month < 1 || month > 12) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "month must be between 1 and 12");
        }
        return employeeRepo.findByHireMonthAndYear(month, year);
    }

    public List<Employee> byHireYear(int year) {
        validateYear(year);
        return employeeRepo.findByHireDateBetween(LocalDate.of(year, 1, 1), LocalDate.of(year, 12, 31));
    }

    private void validateYear(int year) {
        if (year < 1 || year > 9999) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "year must be between 1 and 9999");
        }
    }
}
