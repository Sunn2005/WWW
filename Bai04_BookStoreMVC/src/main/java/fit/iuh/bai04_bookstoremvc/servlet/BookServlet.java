package fit.iuh.bai04_bookstoremvc.servlet;

import fit.iuh.bai04_bookstoremvc.dao.BookDao;
import fit.iuh.bai04_bookstoremvc.model.Book;
import fit.iuh.bai04_bookstoremvc.session.ShoppingCart;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/books")
public class BookServlet extends HttpServlet {

    private final BookDao bookDao = new BookDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");
        if ("detail".equals(action)) {
            int id = Integer.parseInt(req.getParameter("id"));
            req.setAttribute("book", bookDao.findById(id));
            req.getRequestDispatcher("/chitietsach.jsp").forward(req, resp);
            return;
        }

        String keyword = req.getParameter("keyword");
        List<Book> books;
        if (keyword != null && !keyword.isBlank()) {
            books = bookDao.search(keyword.trim());
            req.setAttribute("keyword", keyword.trim());
        } else {
            books = bookDao.findAll();
        }
        req.setAttribute("books", books);
        req.getRequestDispatcher("/danhsach.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        int id = Integer.parseInt(req.getParameter("id"));
        Book book = bookDao.findById(id);
        if (book != null) {
            ShoppingCart.of(req.getSession()).add(book);
        }
        resp.sendRedirect(req.getContextPath() + "/cart");
    }
}
