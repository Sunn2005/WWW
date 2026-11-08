package fit.iuh.bai03_cartmvc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CartItemBean implements Serializable {

   private Product product;
   private int quantity;

   public double getSubtotal() {
       return product.getPrice() * quantity;
   }
}
