package fit.iuh.bai5_jdbc.resource;

import fit.iuh.bai5_jdbc.model.Employee;
import fit.iuh.bai5_jdbc.service.EmployeeService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Map;

@Path("/employees")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EmployeeResource {
    private final EmployeeService service;

    public EmployeeResource() {
        this(new EmployeeService());
    }

    public EmployeeResource(EmployeeService service) {
        this.service = service;
    }

    @GET
    public Response findAll() {
        try {
            return Response.ok(service.findAll()).build();
        } catch (Exception e) {
            return serverError();
        }
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") int id) {
        try {
            return service.findById(id)
                    .map(employee -> Response.ok(employee).build())
                    .orElse(Response.status(Response.Status.NOT_FOUND)
                            .entity(Map.of("message", "Khong tim thay nhan vien")).build());
        } catch (Exception e) {
            return serverError();
        }
    }

    @POST
    public Response create(Employee employee) {
        try {
            return Response.status(Response.Status.CREATED).entity(service.create(employee)).build();
        } catch (IllegalArgumentException e) {
            return badRequest(e);
        } catch (Exception e) {
            return serverError();
        }
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") int id, Employee employee) {
        try {
            if (!service.update(id, employee)) return notFound();
            return Response.ok(employee).build();
        } catch (IllegalArgumentException e) {
            return badRequest(e);
        } catch (Exception e) {
            return serverError();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") int id) {
        try {
            return service.delete(id) ? Response.noContent().build() : notFound();
        } catch (Exception e) {
            return serverError();
        }
    }

    @GET
    @Path("/statistics/average-salary-by-department")
    public Response averageSalaryByDepartment() {
        try {
            return Response.ok(service.averageSalaryByDepartment()).build();
        } catch (Exception e) {
            return serverError();
        }
    }

    @GET
    @Path("/statistics/highest-paid-by-department")
    public Response highestPaidByDepartment() {
        try {
            return Response.ok(service.highestPaidByDepartment()).build();
        } catch (Exception e) {
            return serverError();
        }
    }

    private Response badRequest(IllegalArgumentException e) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(Map.of("message", e.getMessage())).build();
    }

    private Response notFound() {
        return Response.status(Response.Status.NOT_FOUND)
                .entity(Map.of("message", "Khong tim thay nhan vien")).build();
    }

    private Response serverError() {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(Map.of("message", "Loi he thong")).build();
    }
}
