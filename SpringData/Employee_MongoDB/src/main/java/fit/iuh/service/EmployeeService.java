package fit.iuh.service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

import fit.iuh.dto.DepartmentStats;
import fit.iuh.dto.EmployeeForm;
import fit.iuh.dto.EmployeeSearchCriteria;
import fit.iuh.model.Department;
import fit.iuh.model.Employee;
import fit.iuh.model.Position;
import fit.iuh.repo.DepartmentRepository;
import fit.iuh.repo.EmployeeRepository;
import fit.iuh.repo.PositionRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepo;
    private final DepartmentRepository departmentRepo;
    private final PositionRepository positionRepo;
    private final MongoTemplate mongoTemplate;

    public EmployeeService(EmployeeRepository employeeRepo,
                           DepartmentRepository departmentRepo,
                           PositionRepository positionRepo,
                           MongoTemplate mongoTemplate) {
        this.employeeRepo = employeeRepo;
        this.departmentRepo = departmentRepo;
        this.positionRepo = positionRepo;
        this.mongoTemplate = mongoTemplate;
    }

    private void enrichList(List<Employee> list) {
        Map<Integer, String> deptMap = new HashMap<>();
        departmentRepo.findAll().forEach(d -> deptMap.put(d.getId(), d.getName()));
        Map<Integer, String> posMap = new HashMap<>();
        positionRepo.findAll().forEach(p -> posMap.put(p.getId(), p.getTitle()));
        for (Employee e : list) {
            e.setDepartmentName(deptMap.get(e.getDepartmentId()));
            e.setPositionTitle(posMap.get(e.getPositionId()));
        }
    }

    private Integer nextEmployeeId() {
        Query q = new Query().with(Sort.by(Sort.Direction.DESC, "_id")).limit(1);
        Employee max = mongoTemplate.findOne(q, Employee.class);
        return max == null ? 1 : max.getId() + 1;
    }

    public List<Employee> findAll() {
        List<Employee> list = employeeRepo.findAll();
        enrichList(list);
        return list;
    }

    public Employee findById(Integer id) {
        Employee e = employeeRepo.findById(id)
            .orElseThrow(() -> new NoSuchElementException("Không tìm thấy nhân viên id=" + id));
        enrichList(List.of(e));
        return e;
    }

    public List<Employee> search(EmployeeSearchCriteria c) {
        List<Criteria> ands = new ArrayList<>();
        if (c.getId() != null) ands.add(Criteria.where("_id").is(c.getId()));
        if (c.getName() != null && !c.getName().isBlank())
            ands.add(Criteria.where("name").regex(c.getName(), "i"));
        if (c.getMinAge() != null || c.getMaxAge() != null) {
            Criteria age = Criteria.where("age");
            if (c.getMinAge() != null) age.gte(c.getMinAge());
            if (c.getMaxAge() != null) age.lte(c.getMaxAge());
            ands.add(age);
        }
        if (c.getMinSalary() != null) ands.add(Criteria.where("salary").gt(c.getMinSalary()));
        if (c.getPositionId() != null) ands.add(Criteria.where("positionId").is(c.getPositionId()));
        Query query = ands.isEmpty() ? new Query() : new Query(new Criteria().andOperator(ands.toArray(new Criteria[0])));
        List<Employee> list = mongoTemplate.find(query, Employee.class);
        enrichList(list);
        return list;
    }

    public Employee save(Integer id, EmployeeForm form) {
        Employee e = new Employee();
        if (id != null) {
            e.setId(id);
        } else {
            e.setId(nextEmployeeId());
        }
        e.setName(form.getName());
        e.setRole(form.getRole());
        e.setAge(form.getAge());
        e.setSalary(form.getSalary());
        e.setDepartmentId(form.getDepartmentId());
        e.setPositionId(form.getPositionId());
        return employeeRepo.save(e);
    }

    public EmployeeForm getForm(Integer id) {
        Employee e = findById(id);
        EmployeeForm f = new EmployeeForm();
        f.setId(e.getId());
        f.setName(e.getName());
        f.setRole(e.getRole());
        f.setAge(e.getAge());
        f.setSalary(e.getSalary());
        f.setDepartmentId(e.getDepartmentId());
        f.setPositionId(e.getPositionId());
        return f;
    }

    public void delete(Integer id) { employeeRepo.deleteById(id); }

    public List<Department> findAllDepartments() { return departmentRepo.findAll(); }
    public List<Position> findAllPositions() { return positionRepo.findAll(); }

    public List<Employee> findByPositionId(Integer posId) {
        List<Employee> list = employeeRepo.findByPositionId(posId);
        enrichList(list);
        return list;
    }

    public List<Employee> findByDepartmentIdAndPositionId(Integer d, Integer p) {
        List<Employee> list = employeeRepo.findByDepartmentIdAndPositionId(d, p);
        enrichList(list);
        return list;
    }

    public List<DepartmentStats> getDepartmentStats() {
        Aggregation agg = Aggregation.newAggregation(
            Aggregation.lookup("employee", "_id", "departmentId", "emps"),
            Aggregation.project("id", "name")
                .and("_id").as("departmentId")
                .and("name").as("departmentName")
                .and(org.springframework.data.mongodb.core.aggregation.ArrayOperators.Size.lengthOfArray("emps")).as("employeeCount")
                .and(org.springframework.data.mongodb.core.aggregation.AccumulatorOperators.Avg.avgOf("emps.salary")).as("avgSalary")
                .and(org.springframework.data.mongodb.core.aggregation.AccumulatorOperators.Sum.sumOf("emps.salary")).as("totalSalary")
        );
        AggregationResults<DepartmentStats> results =
            mongoTemplate.aggregate(agg, "department", DepartmentStats.class);
        return results.getMappedResults();
    }

    public List<Employee> findTopN(Integer deptId, int n) {
        Pageable pageable = PageRequest.of(0, n, Sort.by(Sort.Direction.DESC, "salary"));
        List<Employee> list = employeeRepo.findByDepartmentId(deptId, pageable).getContent();
        enrichList(list);
        return list;
    }

    public List<Department> findEmptyDepartments() {
        Set<Integer> used = employeeRepo.findAll().stream()
            .map(Employee::getDepartmentId)
            .collect(Collectors.toSet());
        return departmentRepo.findAll().stream()
            .filter(d -> !used.contains(d.getId()))
            .toList();
    }
}
