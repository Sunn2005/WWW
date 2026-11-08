package fit.iuh.demo_restapi.service;

import fit.iuh.demo_restapi.model.Product;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@ApplicationScoped
public class ProductCatalog {
    private final AtomicLong nextId = new AtomicLong(4);
    private final Map<Long, Product> products = new ConcurrentHashMap<>();

    public ProductCatalog() {
        products.put(1L, new Product(1, "Laptop", 1500.00));
        products.put(2L, new Product(2, "MacBook", 1700.00));
        products.put(3L, new Product(3, "Windows Laptop", 1600.00));
    }


    public List<Product> findAll() {
        return products.values().stream()
                .sorted(Comparator.comparingLong(Product::getId))
                .toList();
    }

    public Product findById(long id) {
        return products.get(id);
    }

    public Product create(Product input) {
        long id = nextId.getAndIncrement();
        Product product = new Product(id, input.getName(), input.getPrice());
        products.put(id, product);
        return product;
    }

    public Product update(long id, Product input) {
        if (!products.containsKey(id)) {
            return null;
        }

        Product updatedProduct = new Product(id, input.getName(), input.getPrice());
        products.put(id, updatedProduct);
        return updatedProduct;
    }

    public Product delete(long id) {
        return products.remove(id);
    }
}
