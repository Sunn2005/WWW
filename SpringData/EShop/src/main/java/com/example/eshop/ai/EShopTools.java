package com.example.eshop.ai;

import com.example.eshop.entity.Order;
import com.example.eshop.entity.Product;
import com.example.eshop.repo.CategoryRepo;
import com.example.eshop.repo.OrderRepo;
import com.example.eshop.repo.ProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class EShopTools {

    private final ProductRepo productRepo;
    private final CategoryRepo categoryRepo;
    private final OrderRepo orderRepo;

    @Tool(description = "Tìm sản phẩm trong eShop theo tên, danh mục hoặc giá tối đa. Trả về id, tên, giá, tồn kho và link chi tiết.")
    public String searchProducts(
            @ToolParam(description = "Từ khóa tên sản phẩm, ví dụ: tai nghe, iPhone, chuột") String keyword,
            @ToolParam(description = "Id danh mục, để trống nếu không lọc theo danh mục", required = false) Long categoryId,
            @ToolParam(description = "Giá tối đa tính bằng VND, để trống nếu không lọc theo giá", required = false) Double maxPrice) {
        var page = productRepo.search(keyword, categoryId,
                PageRequest.of(0, 20, Sort.by("price").ascending()));
        var list = page.getContent().stream()
                .filter(p -> maxPrice == null || p.getPrice().doubleValue() <= maxPrice)
                .limit(5)
                .toList();
        if (list.isEmpty()) return "Không tìm thấy sản phẩm nào phù hợp.";
        return list.stream()
                .map(p -> "id=" + p.getId() + " | " + p.getName()
                        + " | " + p.getPrice() + "đ"
                        + " | còn " + p.getQuantity() + " cái"
                        + " | " + p.getCategory().getName()
                        + " | /products/" + p.getId())
                .collect(Collectors.joining("\n"));
    }

    @Tool(description = "Xem chi tiết 1 sản phẩm theo id, gồm mô tả, giá, tồn kho và danh mục.")
    public String getProduct(
            @ToolParam(description = "Id sản phẩm") Long id) {
        Product p = productRepo.findById(id).orElse(null);
        if (p == null) return "Không tìm thấy sản phẩm id = " + id + ".";
        return "id=" + p.getId() + " | " + p.getName()
                + " | " + p.getPrice() + "đ"
                + " | còn " + p.getQuantity() + " cái"
                + " | danh mục: " + p.getCategory().getName()
                + " | mô tả: " + p.getDescription()
                + " | /products/" + p.getId();
    }

    @Tool(description = "Tra cứu trạng thái 1 đơn hàng theo mã đơn. Chỉ trả về đơn của chính người đang đăng nhập.")
    public String getOrderStatus(
            @ToolParam(description = "Mã đơn hàng, ví dụ: 1") Long orderId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth == null ? null : auth.getName();
        if (username == null || "anonymousUser".equals(username)) {
            return "Bạn cần đăng nhập mới tra cứu được đơn hàng.";
        }
        Order o = orderRepo.findById(orderId).orElse(null);
        if (o == null) return "Không tìm thấy đơn hàng #" + orderId + ".";
        if (o.getUser() == null || !username.equals(o.getUser().getUsername())) {
            return "Đơn hàng #" + orderId + " không thuộc tài khoản của bạn.";
        }
        return "Đơn #" + o.getId()
                + " | trạng thái: " + o.getStatus()
                + " | ngày đặt: " + o.getOrderDate()
                + " | tổng tiền: " + o.getTotalAmount() + "đ"
                + " | số món: " + o.getDetails().size();
    }

    @Tool(description = "Liệt kê tất cả danh mục sản phẩm trong eShop.")
    public String listCategories() {
        return categoryRepo.findAllByOrderByNameAsc().stream()
                .map(c -> "id=" + c.getId() + " | " + c.getName())
                .collect(Collectors.joining("\n"));
    }
}
