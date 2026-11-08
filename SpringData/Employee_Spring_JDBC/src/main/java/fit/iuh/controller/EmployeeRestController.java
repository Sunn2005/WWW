package fit.iuh.controller;

import fit.iuh.dto.EmployeeForm;
import fit.iuh.dto.EmployeeSearchCriteria;
import fit.iuh.dto.DepartmentStats;
import fit.iuh.model.Department;
import fit.iuh.model.Position;
import fit.iuh.model.Employee;
import fit.iuh.service.EmployeeService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employees")
public class EmployeeRestController {

    private final EmployeeService employeeService;

    public EmployeeRestController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public List<Employee> getAll() { return employeeService.findAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(employeeService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Employee> create(@Valid @RequestBody EmployeeForm form) {
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeService.save(null, form));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> update(@PathVariable Integer id, @Valid @RequestBody EmployeeForm form) {
        return ResponseEntity.ok(employeeService.save(id, form));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        employeeService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public List<Employee> search(
            @RequestParam(required = false) Integer id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer minAge,
            @RequestParam(required = false) Integer maxAge,
            @RequestParam(required = false) Double minSalary,
            @RequestParam(required = false) Integer positionId) {
        EmployeeSearchCriteria c = new EmployeeSearchCriteria();
        c.setId(id); c.setName(name); c.setMinAge(minAge);
        c.setMaxAge(maxAge); c.setMinSalary(minSalary); c.setPositionId(positionId);
        return employeeService.search(c);
    }

    @GetMapping("/departments")
    public List<Department> departments() { return employeeService.findAllDepartments(); }

    @GetMapping("/positions")
    public List<Position> positions() { return employeeService.findAllPositions(); }

    @GetMapping("/by-position")
    public List<Employee> byPosition(@RequestParam Integer positionId) {
        return employeeService.findByPositionId(positionId);
    }

    @GetMapping("/by-department-position")
    public List<Employee> byDepartmentAndPosition(@RequestParam Integer departmentId,
                                                 @RequestParam Integer positionId) {
        return employeeService.findByDepartmentIdAndPositionId(departmentId, positionId);
    }

    @GetMapping("/stats/department")
    public List<DepartmentStats> stats() { return employeeService.getDepartmentStats(); }

    @GetMapping("/stats/departments-empty")
    public List<Department> emptyDepartments() { return employeeService.findEmptyDepartments(); }

    @GetMapping("/stats/top-salary")
    public List<Employee> topSalary(@RequestParam Integer departmentId,
                                   @RequestParam(defaultValue = "5") int n) {
        if (n < 1) throw new org.springframework.web.server.ResponseStatusException(
            HttpStatus.BAD_REQUEST, "n phải >= 1");
        return employeeService.findTopN(departmentId, n);
    }
}
