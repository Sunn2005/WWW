package fit.iuh.demomvcproduct.servlet;

import fit.iuh.demomvcproduct.dao.ProductDao;
import fit.iuh.demomvcproduct.model.Product;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.sql.DataSource;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "productServlet", value = "/products")
public class ProductServlet extends HttpServlet {
    @Resource(name = "jdbc/productdb")
    private DataSource dataSource;

    private ProductDao productDao;

    @Override
    public void init() throws ServletException {
        if (dataSource == null) {
            try {
                dataSource = (DataSource) new InitialContext()
                        .lookup("java:comp/env/jdbc/productdb");
            } catch (NamingException e) {
                throw new ServletException(
                        "Không tìm thấy DataSource java:comp/env/jdbc/productdb. "
                                + "Kiểm tra META-INF/context.xml và cấu hình Tomcat.", e);
            }
        }
        productDao = new ProductDao(dataSource);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String action = req.getParameter("action");
        if (action == null) {
            action = "list";
        }

        switch (action) {
            case "new" -> showForm(req, resp, new Product(), "insert");
            case "edit" -> showExistingProduct(req, resp, "update");
            case "details" -> showDetails(req, resp);
            default -> listProducts(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String action = req.getParameter("action");
        if ("insert".equals(action)) {
            saveProduct(req, resp, false);
        } else if ("update".equals(action)) {
            saveProduct(req, resp, true);
        } else if ("delete".equals(action)) {
            productDao.deleteProduct(readId(req));
            redirectToList(req, resp);
        } else {
            redirectToList(req, resp);
        }
    }

    private void listProducts(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Product> products = productDao.getAllProducts();
        req.setAttribute("products", products);
        req.getRequestDispatcher("/views/products.jsp").forward(req, resp);
    }

    private void showExistingProduct(HttpServletRequest req, HttpServletResponse resp, String formAction)
            throws ServletException, IOException {
        Product product = findProduct(req, resp);
        if (product != null) {
            showForm(req, resp, product, formAction);
        }
    }

    private void showForm(HttpServletRequest req, HttpServletResponse resp,
                          Product product, String formAction)
            throws ServletException, IOException {
        req.setAttribute("product", product);
        req.setAttribute("formAction", formAction);
        req.getRequestDispatcher("/views/product-form.jsp").forward(req, resp);
    }

    private void showDetails(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Product product = findProduct(req, resp);
        if (product != null) {
            req.setAttribute("product", product);
            req.getRequestDispatcher("/views/product-details.jsp").forward(req, resp);
        }
    }

    private Product findProduct(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = readId(req);
        Product product = productDao.getProductById(id);
        if (product == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Không tìm thấy sản phẩm có mã " + id);
        }
        return product;
    }

    private void saveProduct(HttpServletRequest req, HttpServletResponse resp, boolean isUpdate)
            throws ServletException, IOException {
        Product product = new Product();
        product.setId(readId(req));

        product.setName(req.getParameter("name") == null ? "" : req.getParameter("name").trim());
        try {
            product.setPrice(Double.parseDouble(req.getParameter("price")));
        } catch (NumberFormatException e) {
            req.setAttribute("error", "Giá sản phẩm phải là một số hợp lệ.");
            showForm(req, resp, product, isUpdate ? "update" : "insert");
            return;
        }

        if (product.getId().isBlank() || product.getName().isBlank() || product.getPrice() < 0) {
            req.setAttribute("error", "Mã và tên không được để trống; giá phải lớn hơn hoặc bằng 0.");
            showForm(req, resp, product, isUpdate ? "update" : "insert");
            return;
        }

        try {
            if (isUpdate) {
                productDao.updateProduct(product);
            } else {
                if (productDao.getProductById(product.getId()) != null) {
                    req.setAttribute("error", "Mã sản phẩm đã tồn tại.");
                    showForm(req, resp, product, "insert");
                    return;
                }
                productDao.insertProduct(product);
            }
        } catch (RuntimeException e) {
            req.setAttribute("error", "Không thể lưu sản phẩm. Hãy kiểm tra kết nối và cấu trúc database.");
            showForm(req, resp, product, isUpdate ? "update" : "insert");
            return;
        }
        redirectToList(req, resp);
    }

    private String readId(HttpServletRequest req) throws IOException {
        String id = req.getParameter("id");
        if (id == null || id.isBlank() || id.length() > 20) {
            throw new IOException("Mã sản phẩm không hợp lệ");
        }
        return id.trim();
    }

    private void redirectToList(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.sendRedirect(req.getContextPath() + "/products");
    }
}
