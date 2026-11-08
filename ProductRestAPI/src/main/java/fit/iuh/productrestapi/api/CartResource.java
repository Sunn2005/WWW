package fit.iuh.productrestapi.api;

import fit.iuh.productrestapi.model.CartItem;
import fit.iuh.productrestapi.model.Product;
import fit.iuh.productrestapi.service.ProductCatalog;
import fit.iuh.productrestapi.service.ShoppingCart;
import fit.iuh.productrestapi.session.UserSession;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.math.BigDecimal;
import java.util.List;

@Path("/cart")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
public class CartResource {

    @Inject
    private ProductCatalog productCatalog;

    @Inject
    private UserSession userSession;

    @GET
    public Response getCart() {
        if (!userSession.isLoggedIn()) {
            return unauthorized();
        }
        return Response.ok(toResponse(userSession.getShoppingCart())).build();
    }

    @POST
    @Path("/items/{productId}")
    public Response addItem(@PathParam("productId") long productId) {
        if (!userSession.isLoggedIn()) {
            return unauthorized();
        }

        Product product = productCatalog.findById(productId);

        if (product == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new MessageResponse("Khong tim thay san pham co id = " + productId))
                    .build();
        }

        ShoppingCart cart = userSession.getShoppingCart();
        cart.add(product);
        return Response.ok(toResponse(cart)).build();
    }

    private CartResponse toResponse(ShoppingCart cart) {
        return new CartResponse(cart.getItems(), cart.getItemCount(), cart.getTotal());
    }

    private Response unauthorized() {
        return Response.status(Response.Status.UNAUTHORIZED)
                .entity(new MessageResponse("Ban chua dang nhap."))
                .build();
    }

    public record CartResponse(List<CartItem> items, int itemCount, BigDecimal total) {
    }

    public record MessageResponse(String message) {
    }
}
