package fit.iuh.bai03_cartmvc.servlet;

import fit.iuh.bai03_cartmvc.dao.ProductDao;
import fit.iuh.bai03_cartmvc.model.Product;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet({"/products", "/product"})
public class ProductServlet extends HttpServlet {

    private ProductDao productDao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        productDao = new ProductDao();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idStr = req.getParameter("id");
        if (idStr != null) {
            int id = Integer.parseInt(idStr);
            Product product = productDao.findById(id);
            if (product == null) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Product not found");
                return;
            }
            req.setAttribute("product", product);
            req.getRequestDispatcher("/product-detail.jsp").forward(req, resp);
            return;
        }

        req.setAttribute("products", productDao.findAll());
        req.getRequestDispatcher("/product-list.jsp").forward(req, resp);
    }
}
