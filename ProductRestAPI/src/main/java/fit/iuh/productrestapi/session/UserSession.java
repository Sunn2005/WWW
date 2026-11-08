package fit.iuh.productrestapi.session;

import fit.iuh.productrestapi.service.ShoppingCart;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Named("userSession")
@SessionScoped
public class UserSession implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final String instanceId = UUID.randomUUID().toString();
    private final ShoppingCart shoppingCart = new ShoppingCart();
    private String username;

    public void login(String username) {
        this.username = username;
    }

    public boolean isLoggedIn() {
        return username != null;
    }

    public String getUsername() {
        return username;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public String getInstanceId() {
        return instanceId;
    }
}
