package com.se.demorestdb.service;

import com.se.demorestdb.dto.DepartmentSalary;
import com.se.demorestdb.model.Employee;
import com.se.demorestdb.repo.DepartmentRepo;
import com.se.demorestdb.repo.EmployeeRepo;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.security.PublicKey;
import java.util.List;

@ApplicationScoped
public class EmployeeService {
    @Inject
    private EmployeeRepo employeeRepo;

    @Inject
    DepartmentRepo departmentRepo;

    public List<Employee> findAll(){
        return employeeRepo.findAll();
    }

    public Employee findById(int id){
        return employeeRepo.findById(id);
    }

    public Employee insert(Employee employee){
        if (departmentRepo.findById(employee.getDepartmentId()) == null){
            return null;
        }
        return employeeRepo.insert(employee);
    }

    public List<DepartmentSalary> avgSalaryByDept(){
        return employeeRepo.avgSalaryByDept();
    }
}


