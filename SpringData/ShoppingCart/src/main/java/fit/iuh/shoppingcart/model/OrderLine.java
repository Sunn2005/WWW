package fit.iuh.shoppingcart.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"order", "product"})
@EqualsAndHashCode(exclude = {"order", "product"})
@Entity
@Table(name = "orderlines")
public class OrderLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    private Integer amount;

    // Giá lúc đặt hàng, chụp từ Product.price lúc tạo đơn
    @Column(name = "purchasePrice")
    private Double purchasePrice;
    // Cột purchase_price trong SQL là cột trùng, toàn NULL, bỏ qua, không map.

    @Transient
    public double getLineTotal() {
        if (purchasePrice == null || amount == null) return 0;
        return purchasePrice * amount;
    }
}
