package fit.iuh.bai04_bookstoremvc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItem implements Serializable {
    private Book book;
    private int qty;

    public double getLineTotal() {
        return book.getPrice() * qty;
    }
}
