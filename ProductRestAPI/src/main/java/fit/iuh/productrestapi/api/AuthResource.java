package fit.iuh.productrestapi.api;

import fit.iuh.productrestapi.service.AuthenticationService;
import fit.iuh.productrestapi.session.UserSession;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/auth")
@RequestScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthResource {

    @Inject
    private AuthenticationService authenticationService;

    @Inject
    private UserSession userSession;

    @Context
    private HttpServletRequest request;

    @GET
    @Path("/session")
    public Response getSession() {
        return Response.ok(new SessionResponse(
                userSession.isLoggedIn(),
                userSession.getUsername(),
                userSession.getInstanceId()
        )).build();
    }

    @POST
    @Path("/login")
    public Response login(LoginRequest credentials) {
        if (credentials == null || !authenticationService.authenticate(
                credentials.username(), credentials.password())) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(new MessageResponse("Ten dang nhap hoac mat khau khong dung."))
                    .build();
        }

        userSession.login(credentials.username());
        return Response.ok(new SessionResponse(
                true,
                userSession.getUsername(),
                userSession.getInstanceId()
        )).build();
    }

    @POST
    @Path("/logout")
    public Response logout() {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return Response.ok(new MessageResponse("Dang xuat thanh cong."))
                .build();
    }

    public record LoginRequest(String username, String password) {
    }

    public record SessionResponse(boolean loggedIn, String username, String instanceId) {
    }

    public record MessageResponse(String message) {
    }
}
