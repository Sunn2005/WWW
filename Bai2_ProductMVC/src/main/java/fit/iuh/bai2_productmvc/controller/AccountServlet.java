package fit.iuh.bai2_productmvc.controller;

import fit.iuh.bai2_productmvc.dao.AccountDao;
import fit.iuh.bai2_productmvc.model.Account;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.sql.DataSource;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/registerform")
public class AccountServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Resource(name = "jdbc/storedb")
    private DataSource dataSource;

    private AccountDao accountDao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        if (dataSource == null) {
            throw new ServletException("Cannot find DataSource jdbc/storedb");
        }

        accountDao = new AccountDao(dataSource);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        String firstname = req.getParameter("firstname");
        String lastname = req.getParameter("lastname");
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        int day = Integer.parseInt(req.getParameter("day"));
        int month = Integer.parseInt(req.getParameter("month"));
        int year = Integer.parseInt(req.getParameter("year"));

        LocalDate localDate = LocalDate.of(year, month, day);
        java.sql.Date dob = java.sql.Date.valueOf(localDate);

        Account account = new Account(
                0,
                firstname,
                lastname,
                email,
                password,
                dob
        );

        try {
            accountDao.addAccount(account);
        } catch (Exception e) {
            throw new ServletException("Cannot create account", e);
        }

        // Redirect sang GET de doc lai danh sach moi nhat tu database.
        resp.sendRedirect(req.getContextPath() + "/registerform");
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            List<Account> accounts = accountDao.getAccounts();

            req.setAttribute("accounts", accounts);
            req.getRequestDispatcher("/account-list.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException("Cannot load accounts", e);
        }
    }
}
