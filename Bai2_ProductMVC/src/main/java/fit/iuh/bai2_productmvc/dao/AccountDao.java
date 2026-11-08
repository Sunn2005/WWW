package fit.iuh.bai2_productmvc.dao;

import fit.iuh.bai2_productmvc.model.Account;
import fit.iuh.bai2_productmvc.util.DBUtil;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AccountDao {

    private final DBUtil dbUtil;

    public AccountDao(DataSource dataSource) {
        this.dbUtil = new DBUtil(dataSource);
    }

    public List<Account> getAccounts() throws Exception {

        List<Account> accounts = new ArrayList<>();

        String sql = "SELECT ID, FIRSTNAME, LASTNAME, EMAIL, PASSWORD, DATEOFBIRTH " +
                "FROM accounts ORDER BY ID";

        try (Connection conn = dbUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int id = rs.getInt("ID");
                String fname = rs.getString("FIRSTNAME");
                String lname = rs.getString("LASTNAME");
                String email = rs.getString("EMAIL");
                String password = rs.getString("PASSWORD");
                Date dateofbirth = rs.getDate("DATEOFBIRTH");

                Account acc = new Account(
                        id,
                        fname,
                        lname,
                        email,
                        password,
                        dateofbirth
                );

                accounts.add(acc);
            }
        }

        return accounts;
    }


    // Thêm account
    public void addAccount(Account acc) throws Exception {

        String sql = "INSERT INTO accounts " +
                "(FIRSTNAME, LASTNAME, EMAIL, PASSWORD, DATEOFBIRTH) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = dbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, acc.getFirstName());
            ps.setString(2, acc.getLastName());
            ps.setString(3, acc.getEmail());
            ps.setString(4, acc.getPassword());
            ps.setDate(5, new java.sql.Date(acc.getDateOfBirth().getTime()));

            ps.executeUpdate();
        }
    }
}
