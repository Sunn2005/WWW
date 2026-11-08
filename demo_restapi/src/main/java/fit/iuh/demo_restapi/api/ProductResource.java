package fit.iuh.demo_restapi.api;

import fit.iuh.demo_restapi.model.Product;
import fit.iuh.demo_restapi.service.ProductCatalog;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/products")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductResource {

    @Inject
    private ProductCatalog productCatalog;

    @GET
    public Response getAllProducts() {
        return Response.ok(productCatalog.findAll()).build();
    }

    @GET
    @Path("/{id}")
    public Response getProductById(@PathParam("id") long id) {
        Product product = getOrThrow(productCatalog.findById(id), id);
        return Response.ok(product).build();
    }

    @POST
    public Response createProduct(Product product) {
        return Response.status(Response.Status.CREATED)
                .entity(productCatalog.create(product))
                .build();
    }

    @PUT
    @Path("/{id}")
    public Response updateProduct(@PathParam("id") long id, Product product) {
        Product updated = getOrThrow(productCatalog.update(id, product), id);
        return Response.ok(updated).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteProduct(@PathParam("id") long id) {
        Product deleted = getOrThrow(productCatalog.delete(id), id);
        return Response.ok(deleted).build();
    }

    private Product getOrThrow(Product product, long id) {
        if (product == null) {
            throw new NotFoundException("Không tìm thấy product có id " + id);
        }
        return product;
    }
}
