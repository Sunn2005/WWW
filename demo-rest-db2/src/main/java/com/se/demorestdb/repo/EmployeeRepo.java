package com.se.demorestdb.repo;

import com.se.demorestdb.dto.DepartmentSalary;
import com.se.demorestdb.model.Employee;
import jakarta.enterprise.context.ApplicationScoped;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class EmployeeRepo {
    private DataSource dataSource;

    private DataSource getDataSource(){
        if (dataSource == null){
            try {
                dataSource = (DataSource) new InitialContext()
                        .lookup("java:comp/env/jdbc/hrdb");
            } catch (Exception e) {
                throw new RuntimeException("Khong tim thay datasource");
            }
        }
        return dataSource;
    }

    public List<Employee> findAll(){
        String sql = "select * from employees";

        List<Employee> result = new ArrayList<>();
        try (Connection con = getDataSource().getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery();
        ){
            while (rs.next()){
                result.add(mapEmployee(rs));
            }
            return result;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public Employee findById(int id){
        String sql = "select * from employees where ID=?";
        try (Connection con = getDataSource().getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
        ){
            ps.setInt(1, id);
           try (ResultSet rs = ps.executeQuery()){
               return rs.next() ? mapEmployee(rs) : null;
           }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Employee insert(Employee employee){
        String sql = "insert into employees(name, role, salary, department_id) values (?,?,?,?)";
        try (Connection con = getDataSource().getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ){
            setValues(ps, employee);
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()){
               if (keys.next()){
                   employee.setId(keys.getInt(1));
               }
            }
            return employee;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<DepartmentSalary> avgSalaryByDept() {
        String sql = "SELECT e.department_id AS dept_id, AVG(e.salary) AS avg_salary " +
                "FROM employees e " +
                "GROUP BY e.department_id " +
                "ORDER BY e.department_id";

        List<DepartmentSalary> result = new ArrayList<>();
        try (Connection con = getDataSource().getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                result.add(new DepartmentSalary(
                        rs.getInt("dept_id"),
                        "Phong " + rs.getString("dept_id"),  // tạm: nếu có bảng departments thì JOIN lấy tên thật
                        rs.getDouble("avg_salary")        // giờ alias đã tồn tại
                ));
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


//    public List<DepartmentSalary> avgSalaryByDept(){
//        String sql = "select e.id, e.name, AVG(e.salary) from employees e " +
//                " where e.salary = (select MAX(e2.salary) from employees e2 " +
//                " where e2.department_id = e.department_id) order by e.department_id, e.id";
//
//        List<DepartmentSalary> result = new ArrayList<>();
//        try (Connection con = getDataSource().getConnection();
//             PreparedStatement ps = con.prepareStatement(sql);
//             ResultSet rs = ps.executeQuery()
//        ){
//            while (rs.next()){
//                result.add(new DepartmentSalary(
//                        rs.getInt("id"),
//                        rs.getString("name"),
//                        rs.getDouble("avg_salary")
//                ));
//            }
//            return result;
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }

    private void setValues(PreparedStatement ps, Employee employee) throws SQLException {
        ps.setString(1, employee.getName());
        ps.setString(2, employee.getRole());
        ps.setDouble(3, employee.getSalary());
        ps.setInt(4, employee.getDepartmentId());
    }

    private Employee mapEmployee(ResultSet rs) throws SQLException {
        return new Employee(
                rs.getInt("ID"),
                rs.getString("name"),
                rs.getDouble("salary"),
                rs.getString("role"),
                rs.getInt("department_id")

        );
    }


}
