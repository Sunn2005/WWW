package fit.iuh.bai04_bookstoremvc.servlet;

import fit.iuh.bai04_bookstoremvc.session.ShoppingCart;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("cart", ShoppingCart.of(req.getSession()));
        req.getRequestDispatcher("/thanhtoan.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        ShoppingCart cart = ShoppingCart.of(req.getSession());
        req.setAttribute("fullname", req.getParameter("fullname"));
        req.setAttribute("address", req.getParameter("address"));
        req.setAttribute("payment", req.getParameter("payment"));
        req.setAttribute("paidTotal", cart.getTotal());
        req.setAttribute("message", "Đặt hàng thành công");
        cart.clear();
        req.setAttribute("cart", cart);
        req.getRequestDispatcher("/thanhtoan.jsp").forward(req, resp);
    }
}
