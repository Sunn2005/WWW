package fit.iuh.shoppingcart.repo;

import fit.iuh.shoppingcart.model.Order;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepo extends JpaRepository<Order, Integer> {

    // Lịch sử mua hàng của 1 khách, mới nhất trước
    List<Order> findByCustomerIdOrderByDateDesc(Integer customerId);

    List<Order> findByCustomerId(Integer customerId);

    List<Order> findAllByOrderByDateDesc();
}
