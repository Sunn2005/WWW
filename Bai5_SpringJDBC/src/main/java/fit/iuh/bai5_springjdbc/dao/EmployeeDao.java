package fit.iuh.bai5_springjdbc.dao;

import fit.iuh.bai5_springjdbc.enitity.Employee;

import java.util.List;

public interface EmployeeDao {
    void save(Employee employee);
    void update(Employee emoployee);
    Employee getById(int id);
    List<Employee> getAll();
    void deleteById(int id);

}
