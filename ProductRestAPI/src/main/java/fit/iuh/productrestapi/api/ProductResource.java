package fit.iuh.productrestapi.api;

import fit.iuh.productrestapi.service.ProductCatalog;
import fit.iuh.productrestapi.session.UserSession;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import fit.iuh.productrestapi.model.Product;

@Path("/products")
@RequestScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProductResource {

    @Inject
    private ProductCatalog productCatalog;

    @Inject
    private UserSession userSession;

    @GET
    public Response getAllProducts() {
        if (!userSession.isLoggedIn()) {
            return unauthorized();
        }
        return Response.ok(productCatalog.findAll()).build();
    }

    @GET
    @Path("/{id}")
    public Response getProductById(@PathParam("id") long id) {
        if (!userSession.isLoggedIn()) {
            return unauthorized();
        }

        Product product = productCatalog.findById(id);

        if (product == null) {
            return notFound(id);
        }

        return Response.ok(product).build();
    }

    @POST
    public Response createProduct(Product product) {
        if (!userSession.isLoggedIn()) {
            return unauthorized();
        }

        Product createdProduct = productCatalog.create(
                product.getName(),
                product.getPrice()
        );

        return Response.status(Response.Status.CREATED)
                .entity(createdProduct)
                .build();
    }

    @PUT
    @Path("/{id}")
    public Response updateProduct(@PathParam("id") long id, Product product) {
        if (!userSession.isLoggedIn()) {
            return unauthorized();
        }

        Product updatedProduct = productCatalog.update(
                id,
                product.getName(),
                product.getPrice()
        );

        if (updatedProduct == null) {
            return notFound(id);
        }

        return Response.ok(updatedProduct).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteProduct(@PathParam("id") long id) {
        if (!userSession.isLoggedIn()) {
            return unauthorized();
        }

        if (!productCatalog.delete(id)) {
            return notFound(id);
        }
        return Response.noContent().build();
    }

    private Response notFound(long id) {
        return Response.status(Response.Status.NOT_FOUND)
                .entity(new MessageResponse("Khong tim thay san pham co id = " + id))
                .build();
    }

    private Response unauthorized() {
        return Response.status(Response.Status.UNAUTHORIZED)
                .entity(new MessageResponse("Ban chua dang nhap."))
                .build();
    }

    public record MessageResponse(String message) {
    }
}
