package fit.iuh.demo_restapi.service;

import fit.iuh.demo_restapi.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductCatalogTest {
    private ProductCatalog catalog;

    @BeforeEach
    void setUp() {
        catalog = new ProductCatalog();
    }

    @Test
    void shouldReturnSeedProducts() {
        assertEquals(3, catalog.findAll().size());
        assertEquals("Laptop", catalog.findById(1).getName());
    }

    @Test
    void shouldCreateProductWithGeneratedId() {
        Product created = catalog.create(new Product(0, "Mouse", 25.5));

        assertEquals(4, created.getId());
        assertSame(created, catalog.findById(4));
    }

    @Test
    void shouldUpdateExistingProduct() {
        Product updated = catalog.update(1, new Product(0, "Gaming Laptop", 2000));

        assertNotNull(updated);
        assertEquals("Gaming Laptop", catalog.findById(1).getName());
        assertEquals(2000, catalog.findById(1).getPrice());
    }

    @Test
    void shouldNotUpdateMissingProduct() {
        assertNull(catalog.update(99, new Product(0, "Unknown", 10)));
    }

    @Test
    void shouldDeleteProduct() {
        assertNotNull(catalog.delete(1));
        assertNull(catalog.findById(1));
        assertNull(catalog.delete(1));
    }
}
