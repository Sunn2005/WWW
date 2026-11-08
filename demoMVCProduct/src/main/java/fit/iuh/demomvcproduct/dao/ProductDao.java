package fit.iuh.demomvcproduct.dao;

import fit.iuh.demomvcproduct.model.Product;
import fit.iuh.demomvcproduct.util.DBUtil;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDao {
    private final DBUtil dbUtil;

    public ProductDao(DataSource dataSource){
        this.dbUtil = new DBUtil(dataSource);
    }

    public List<Product> getAllProducts() {
        List<Product> listProducts = new ArrayList<>();
        String sql = "SELECT id, name, price FROM products ORDER BY id";

        try (Connection connection = dbUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet rs = preparedStatement.executeQuery()) {
            while (rs.next()) {
                listProducts.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Không thể lấy danh sách sản phẩm", e);
        }
        return listProducts;
    }

    public Product getProductById(String id) {
        String sql = "SELECT id, name, price FROM products WHERE id = ?";

        try (Connection connection = dbUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, id);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                return rs.next() ? mapRow(rs) : null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Không thể tìm sản phẩm có mã " + id, e);
        }
    }

    public void insertProduct(Product product) {
        String sql = "INSERT INTO products(id, name, price) VALUES (?, ?, ?)";
        try (Connection connection = dbUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, product.getId());
            preparedStatement.setString(2, product.getName());
            preparedStatement.setDouble(3, product.getPrice());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Không thể thêm sản phẩm", e);
        }
    }

    public void updateProduct(Product product) {
        String sql = "UPDATE products SET name = ?, price = ? WHERE id = ?";
        try (Connection connection = dbUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getPrice());
            preparedStatement.setString(3, product.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Không thể cập nhật sản phẩm", e);
        }
    }

    public void deleteProduct(String id) {
        String sql = "DELETE FROM products WHERE id = ?";
        try (Connection connection = dbUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Không thể xóa sản phẩm có mã " + id, e);
        }
    }

    private Product mapRow(ResultSet rs) throws SQLException {
        return new Product(
                rs.getString("id"),
                rs.getString("name"),
                rs.getDouble("price")
        );
    }
}
