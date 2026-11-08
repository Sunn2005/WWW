package fit.iuh.dao;


import fit.iuh.dto.DepartmentStats;
import fit.iuh.model.Department;

import java.util.List;

public interface DepartmentDao {
    List<Department> findAll();
    List<DepartmentStats> getStats();
    List<Department> findEmptyDepartments();
}
