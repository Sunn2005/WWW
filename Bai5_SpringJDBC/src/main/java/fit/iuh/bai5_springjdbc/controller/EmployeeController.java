package fit.iuh.bai5_springjdbc.controller;

import fit.iuh.bai5_springjdbc.dao.EmployeeDao;
import fit.iuh.bai5_springjdbc.dao.impl.EmployeeDaoImpl;
import fit.iuh.bai5_springjdbc.enitity.Employee;
import jakarta.websocket.server.PathParam;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    private EmployeeDaoImpl employeeDaoimpl;

    public EmployeeController(EmployeeDaoImpl employeeDaoimpl) {
        this.employeeDaoimpl = employeeDaoimpl;
    }
    @GetMapping
    public List<Employee> getAll(){
        return employeeDaoimpl.getAll();
    }

    @PostMapping("/new")
    public Employee newEmployee(@RequestBody Employee employee) {
        employeeDaoimpl.save(employee);
        return employee;
    }
}
