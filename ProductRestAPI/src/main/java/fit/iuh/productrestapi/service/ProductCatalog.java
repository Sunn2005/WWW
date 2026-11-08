package fit.iuh.productrestapi.service;

import fit.iuh.productrestapi.model.Product;
import jakarta.enterprise.context.ApplicationScoped;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class ProductCatalog {
    private final List<Product> products = new ArrayList<>(List.of(
            new Product(1, "Laptop", new BigDecimal("1500.00")),
            new Product(2, "Keyboard", new BigDecimal("80.00")),
            new Product(3, "Mouse", new BigDecimal("40.00"))
    ));
    private long nextId = 4;

    public List<Product> findAll() {
        return products;
    }

    public Product findById(long id) {
        return products.stream()
                .filter(product -> product.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public Product create(String name, BigDecimal price) {
        Product product = new Product(nextId++, name, price);
        products.add(product);
        return product;
    }

    public Product update(long id, String name, BigDecimal price) {
        Product product = findById(id);

        if (product == null) {
            return null;
        }

        product.setName(name);
        product.setPrice(price);
        return product;
    }

    public boolean delete(long id) {
        Product product = findById(id);

        if (product == null) {
            return false;
        }

        products.remove(product);
        return true;
    }
}
