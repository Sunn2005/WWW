package fit.iuh.productrestapi.controller;

import fit.iuh.productrestapi.model.User;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    private static final List<User> USER_LIST = new CopyOnWriteArrayList<>(List.of(
            new User(1, "Mai Hoang", "hoang@gmail.com"),
            new User(2, "Lam Tong", "tong@gmail.com")
    ));

    @GET
    @Path("/view")
    @Produces(MediaType.TEXT_PLAIN)
    public String viewUser() {
        return "Thanh Xuan";
    }

    @GET
    @Path("/add/{a}/{b}")
    @Produces(MediaType.TEXT_PLAIN)
    public int add(@PathParam("a") int a, @PathParam("b") int b) {
        return a + b;
    }

    @GET
    @Path("/getmark")
    @Produces(MediaType.TEXT_PLAIN)
    public String getMark(@QueryParam("diem") String diem,
                          @QueryParam("ten") String ten) {
        return "Chao ban " + ten + ", gpa: " + diem;
    }

    @GET
    public Response getAllUsers() {
        return Response.ok(USER_LIST).build();
    }

    @GET
    @Path("/{id}")
    public Response getUserById(@PathParam("id") int id) {
        return USER_LIST.stream()
                .filter(user -> user.getId() == id)
                .findFirst()
                .map(user -> Response.ok(user).build())
                .orElseGet(() -> notFound(id));
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createUser(User user) {
        if (user == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("User khong hop le")
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        }

        USER_LIST.add(user);
        return Response.status(Response.Status.CREATED).entity(user).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateUser(@PathParam("id") int id, User updatedUser) {
        if (updatedUser == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("User khong hop le")
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        }

        return USER_LIST.stream()
                .filter(user -> user.getId() == id)
                .findFirst()
                .map(user -> {
                    user.setName(updatedUser.getName());
                    user.setEmail(updatedUser.getEmail());
                    return Response.ok(user).build();
                })
                .orElseGet(() -> notFound(id));
    }

    @DELETE
    @Path("/{id}")
    public Response deleteUser(@PathParam("id") int id) {
        User user = USER_LIST.stream()
                .filter(candidate -> candidate.getId() == id)
                .findFirst()
                .orElse(null);

        if (user == null) {
            return notFound(id);
        }

        USER_LIST.remove(user);
        return Response.ok(user).build();
    }

    private Response notFound(int id) {
        return Response.status(Response.Status.NOT_FOUND)
                .entity("Khong tim thay user co id = " + id)
                .type(MediaType.TEXT_PLAIN)
                .build();
    }
}
