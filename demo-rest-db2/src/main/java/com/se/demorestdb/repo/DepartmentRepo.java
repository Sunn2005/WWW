package com.se.demorestdb.repo;

import com.se.demorestdb.model.Department;
import com.se.demorestdb.model.Employee;
import jakarta.enterprise.context.ApplicationScoped;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@ApplicationScoped
public class DepartmentRepo {
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

    public Department findById(int id){
        String sql = "select * from departments where id=?";
        try (Connection con = getDataSource().getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
        ){
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()){
                return rs.next() ? mapDepartment(rs) : null;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Department mapDepartment(ResultSet rs) throws SQLException {
        return new Department(
                rs.getInt("id"),
                rs.getString("name")
        );
    }
}
