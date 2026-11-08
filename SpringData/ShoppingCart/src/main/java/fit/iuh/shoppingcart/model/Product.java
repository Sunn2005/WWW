package fit.iuh.shoppingcart.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"comments", "orderLines"})
@EqualsAndHashCode(exclude = {"comments", "orderLines"})
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private Double price;

    @Column(name = "inStock")
    private Boolean inStock;
    // Cột in_stock trong SQL là cột trùng, bỏ qua, không map.

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "product")
    private List<OrderLine> orderLines = new ArrayList<>();
}
