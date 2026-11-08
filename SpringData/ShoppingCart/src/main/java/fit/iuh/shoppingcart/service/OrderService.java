package fit.iuh.shoppingcart.service;

import fit.iuh.shoppingcart.model.Order;
import fit.iuh.shoppingcart.repo.OrderRepo;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    private final OrderRepo orderRepo;

    public OrderService(OrderRepo orderRepo) {
        this.orderRepo = orderRepo;
    }

    public List<Order> findAll() {
        return orderRepo.findAllByOrderByDateDesc();
    }

    @Transactional(readOnly = true)
    public Order findById(int id) {
        Order order = orderRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy Order id=" + id));
        // Chạm vào orderLines để load chi tiết đơn trong cùng transaction
        order.getOrderLines().size();
        return order;
    }

    // Lịch sử mua hàng của 1 khách
    public List<Order> findByCustomerId(int customerId) {
        return orderRepo.findByCustomerIdOrderByDateDesc(customerId);
    }
}
