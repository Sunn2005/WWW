package fit.iuh.bai05_gk.servlet;

import fit.iuh.bai05_gk.dao.DepartmentDao;
import fit.iuh.bai05_gk.model.Department;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/departments")
public class DepartmentServlet extends HttpServlet {

    private DepartmentDao departmentDao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        departmentDao = new DepartmentDao();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action != null){
            Long id = Long.parseLong(req.getParameter("id"));
            Department department = departmentDao.findById(id);
            req.setAttribute("department", department);

            req.getRequestDispatcher("department-form.jsp").forward(req, resp);
            return;
        }

        String name = req.getParameter("name");
        if (name != null)
            req.setAttribute("departments", departmentDao.findByName(name));
        else
            req.setAttribute("departments", departmentDao.findAll());

        req.getRequestDispatcher("department-list.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if (action != null) {
            switch (action) {
                case "DELETE" -> {
                    Long id = Long.parseLong(req.getParameter("id"));
                    departmentDao.delete(id);
                    resp.sendRedirect("departments");
                }

                case "SAVE" -> {
                    String id = req.getParameter("id");
                    String name = req.getParameter("name");

                    if (id != null){
                        Department newDepartment = new Department(null, name, null);
                        departmentDao.update(Long.parseLong(id), newDepartment);
                    } else {
                        Department newDepartment = new Department(null, name, null);
                        departmentDao.save(newDepartment);
                    }

                    resp.sendRedirect("departments");
                }
            }
        }
    }
}
