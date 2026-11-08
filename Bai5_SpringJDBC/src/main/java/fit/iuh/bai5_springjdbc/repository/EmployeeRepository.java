package fit.iuh.bai5_springjdbc.repository;

import fit.iuh.bai5_springjdbc.enitity.Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Integer> {


}
