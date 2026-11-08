package fit.iuh.bai04_bookstoremvc.session;

import fit.iuh.bai04_bookstoremvc.model.Book;
import fit.iuh.bai04_bookstoremvc.model.CartItem;
import jakarta.servlet.http.HttpSession;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class ShoppingCart implements Serializable {

    private final Map<Integer, CartItem> items = new LinkedHashMap<>();

    public static ShoppingCart of(HttpSession session) {
        ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
        if (cart == null) {
            cart = new ShoppingCart();
            session.setAttribute("cart", cart);
        }
        return cart;
    }

    public void add(Book book) {
        CartItem item = items.get(book.getId());
        if (item == null) {
            items.put(book.getId(), new CartItem(book, 1));
        } else {
            item.setQty(item.getQty() + 1);
        }
    }

    public void remove(int bookId) {
        items.remove(bookId);
    }

    public Collection<CartItem> getItems() {
        return items.values();
    }

    public double getTotal() {
        double total = 0;
        for (CartItem item : items.values()) {
            total += item.getLineTotal();
        }
        return total;
    }

    public void clear() {
        items.clear();
    }
}
