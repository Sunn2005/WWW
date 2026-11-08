package fit.iuh.controller;

import fit.iuh.dto.EmployeeForm;
import fit.iuh.dto.EmployeeSearchCriteria;
import fit.iuh.model.Employee;
import fit.iuh.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        try { return ResponseEntity.ok(employeeService.findById(id)); }
        catch (Exception e) { return ResponseEntity.notFound().build(); }
    }

    @PostMapping
    public ResponseEntity<Employee> create(@Valid @RequestBody EmployeeForm form) {
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeService.save(null, form));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> update(@PathVariable Integer id, @Valid @RequestBody EmployeeForm form) {
        try { return ResponseEntity.ok(employeeService.save(id, form)); }
        catch (Exception e) { return ResponseEntity.notFound().build(); }
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
}
