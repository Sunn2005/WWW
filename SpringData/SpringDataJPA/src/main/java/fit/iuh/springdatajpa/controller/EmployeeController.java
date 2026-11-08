package fit.iuh.springdatajpa.controller;

import fit.iuh.springdatajpa.model.Department;
import fit.iuh.springdatajpa.model.Employee;
import fit.iuh.springdatajpa.dto.EmployeeSummary;
import fit.iuh.springdatajpa.dto.EmployeeGroupStats;
import fit.iuh.springdatajpa.dto.HireMonthStats;
import fit.iuh.springdatajpa.dto.HireYearStats;
import fit.iuh.springdatajpa.service.DepartmentService;
import fit.iuh.springdatajpa.service.EmployeeService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    private final EmployeeService employeeService;
    private final DepartmentService departmentService;

    public EmployeeController(EmployeeService employeeService, DepartmentService departmentService) {
        this.employeeService = employeeService;
        this.departmentService = departmentService;
    }

    @GetMapping
    public List<Employee> getAll() { return employeeService.getAll(); }

    @GetMapping("/{id}")
    public Employee getById(@PathVariable Integer id) {
        return employeeService.getById(id);
    }

    @PostMapping
    public Employee create(@RequestBody Employee e) { return employeeService.create(e); }

    @PutMapping("/{id}")
    public Employee update(@PathVariable Integer id, @RequestBody Employee e) {
        return employeeService.update(id, e);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) { employeeService.delete(id); }

    @GetMapping("/search/name")
    public List<Employee> byName(@RequestParam String name) {
        return employeeService.byName(name);
    }

    @GetMapping("/search/age")
    public List<Employee> byAge(@RequestParam Integer min, @RequestParam Integer max) {
        return employeeService.byAge(min, max);
    }

    @GetMapping("/search/salary")
    public List<Employee> bySalary(@RequestParam Double min) {
        return employeeService.bySalary(min);
    }

    @GetMapping("/search/position/{positionId}")
    public List<Employee> byPosition(@PathVariable Integer positionId) {
        return employeeService.byPosition(positionId);
    }

    @GetMapping("/search/department-position")
    public List<Employee> byDeptAndPosition(@RequestParam Integer departmentId, @RequestParam Integer positionId) {
        return employeeService.byDeptAndPosition(departmentId, positionId);
    }

    @GetMapping("/stats/department")
    public List<Object[]> stats() { return employeeService.statsByDepartment(); }

    @GetMapping("/stats/summary")
    public EmployeeSummary summary() {
        return employeeService.summary();
    }

    @GetMapping("/stats/department-details")
    public List<EmployeeGroupStats> departmentStatistics(@RequestParam(defaultValue = "0") long minEmployees) {
        return employeeService.departmentStatistics(minEmployees);
    }

    @GetMapping("/stats/position")
    public List<EmployeeGroupStats> positionStatistics() {
        return employeeService.positionStatistics();
    }

    @GetMapping("/stats/hire-monthly")
    public List<HireMonthStats> monthlyHireStatistics(@RequestParam int year) {
        return employeeService.monthlyHireStatistics(year);
    }

    @GetMapping("/stats/hire-yearly")
    public List<HireYearStats> yearlyHireStatistics() {
        return employeeService.yearlyHireStatistics();
    }

    @GetMapping("/stats/top-salary")
    public List<Employee> topSalary(@RequestParam Integer departmentId, @RequestParam(defaultValue = "5") int n) {
        return employeeService.topSalary(departmentId, n);
    }

    @GetMapping("/stats/departments-empty")
    public List<Department> emptyDepartments() {
        return departmentService.findDepartmentsWithoutEmployees();
    }

    @GetMapping("/search/hire-date")
    public List<Employee> byHireDate(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return employeeService.byHireDate(date);
    }

    @GetMapping("/search/hired-before")
    public List<Employee> hiredBefore(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return employeeService.hiredBefore(date);
    }

    @GetMapping("/search/hired-after")
    public List<Employee> hiredAfter(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return employeeService.hiredAfter(date);
    }

    @GetMapping("/search/hire-date-between")
    public List<Employee> hiredBetween(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return employeeService.hiredBetween(startDate, endDate);
    }

    @GetMapping("/search/hire-month")
    public List<Employee> byHireMonthAndYear(@RequestParam int month, @RequestParam int year) {
        return employeeService.byHireMonthAndYear(month, year);
    }

    @GetMapping("/search/hire-year")
    public List<Employee> byHireYear(@RequestParam int year) {
        return employeeService.byHireYear(year);
    }
}
