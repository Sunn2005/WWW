package fit.iuh.shoppingcart.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "orderLines")
@EqualsAndHashCode(exclude = "orderLines")
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "date")
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderLine> orderLines = new ArrayList<>();

    // Total không lưu cột, tính từ OrderLine mỗi lần hiển thị
    @Transient
    public double getTotal() {
        if (orderLines == null) return 0;
        double sum = 0;
        for (OrderLine line : orderLines) {
            if (line.getPurchasePrice() != null && line.getAmount() != null) {
                sum += line.getPurchasePrice() * line.getAmount();
            }
        }
        return sum;
    }
}
