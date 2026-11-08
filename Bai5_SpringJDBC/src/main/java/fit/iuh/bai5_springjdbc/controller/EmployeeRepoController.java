package fit.iuh.bai5_springjdbc.controller;

import fit.iuh.bai5_springjdbc.enitity.Employee;
import fit.iuh.bai5_springjdbc.repository.EmployeeRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/emplrepo")
public class EmployeeRepoController {
    private EmployeeRepository employeeRepository;

    public EmployeeRepoController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @GetMapping
    public List<Employee> getAllEmpl(){
        return (List<Employee>) employeeRepository.findAll();
    }

    @PostMapping
    public void newEmpl(Employee employee){
        employeeRepository.save(employee);
    }
}
