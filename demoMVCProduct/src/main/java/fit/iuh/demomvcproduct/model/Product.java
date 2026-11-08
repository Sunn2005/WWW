package fit.iuh.demomvcproduct.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class Product {
    private String id;
    private String name;
    private double price;
}
