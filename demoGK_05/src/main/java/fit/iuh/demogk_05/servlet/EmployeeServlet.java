package fit.iuh.demogk_05.servlet;

import fit.iuh.demogk_05.dao.DepartmentDao;
import fit.iuh.demogk_05.dao.EmployeeDao;
import fit.iuh.demogk_05.model.Department;
import fit.iuh.demogk_05.model.Employee;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/employees")
public class EmployeeServlet extends HttpServlet {
    private EmployeeDao employeeDao;
    private DepartmentDao departmentDao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        employeeDao = new EmployeeDao();
        departmentDao = new DepartmentDao();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if (action!= null){
            req.setAttribute("departments", departmentDao.findAll());

            if (action.equals("EDIT")){
                Long id = Long.parseLong(req.getParameter("id"));
                req.setAttribute("employees", employeeDao.findById(id));
            }
            req.getRequestDispatcher("employee-form.jsp").forward(req, resp);
        } else {
            String name = req.getParameter("name");

            if (name!= null){
                req.setAttribute("employees", employeeDao.findByName(name));
            }else {
                req.setAttribute("employees", employeeDao.findAll());
            }
            req.getRequestDispatcher("employee-list.jsp").forward(req,resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action != null){
            switch (action){
                case "DELETE" -> {
                    Long id = Long.parseLong(req.getParameter("id"));
                    employeeDao.delete(id);
                    resp.sendRedirect("employees");
                }
                case "SAVE" ->{
                    String id = req.getParameter("id");
                    String name = req.getParameter("name");
                    double salary = Double.parseDouble(req.getParameter("salary"));
                    String deptId = req.getParameter("deptId");

                    Department department = departmentDao.findById(Long.parseLong(deptId));
                    Employee employee = new Employee(
                            null,
                            name,
                            salary,
                            department
                    );
                    if (id != null){
                        employeeDao.update(Long.parseLong(id), employee);
                    } else {
                        employeeDao.save(employee);
                    }
                    resp.sendRedirect("employees");
                }
            }
        }
    }
}
