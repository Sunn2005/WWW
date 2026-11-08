package fit.iuh.springdatajpa.service;

import fit.iuh.springdatajpa.model.Department;
import fit.iuh.springdatajpa.repo.DepartmentRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class DepartmentService {
    private final DepartmentRepo departmentRepo;

    public DepartmentService(DepartmentRepo departmentRepo) {
        this.departmentRepo = departmentRepo;
    }

    public List<Department> findDepartmentsWithoutEmployees() {
        return departmentRepo.findDepartmentsWithoutEmployees();
    }
}
