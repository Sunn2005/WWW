package fit.iuh.bai05_gk.api;

import fit.iuh.bai05_gk.dao.DepartmentDao;
import fit.iuh.bai05_gk.model.Department;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Map;

@Path("/departments")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DepartmentResource {

    private final DepartmentDao departmentDao = new DepartmentDao();

    @GET
    public Response findAll(@QueryParam("name") String name) {
        if (name != null && !name.isBlank()) {
            return Response.ok(departmentDao.findByName(name)).build();
        }
        return Response.ok(departmentDao.findAll()).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        Department department = departmentDao.findById(id);
        if (department == null) {
            return notFound("Department không tồn tại");
        }
        return Response.ok(department).build();
    }

    @POST
    public Response create(Department department) {
        if (department == null || department.getName() == null
                || department.getName().isBlank()) {
            return badRequest("Tên department không được để trống");
        }

        department.setId(null);
        department.setEmployees(null);
        departmentDao.save(department);
        return Response.status(Response.Status.CREATED).entity(department).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, Department department) {
        if (departmentDao.findById(id) == null) {
            return notFound("Department không tồn tại");
        }
        if (department == null || department.getName() == null
                || department.getName().isBlank()) {
            return badRequest("Tên department không được để trống");
        }

        departmentDao.update(id, department);
        return Response.ok(departmentDao.findById(id)).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        if (departmentDao.findById(id) == null) {
            return notFound("Department không tồn tại");
        }
        departmentDao.delete(id);
        return Response.noContent().build();
    }

    private Response badRequest(String message) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(Map.of("message", message)).build();
    }

    private Response notFound(String message) {
        return Response.status(Response.Status.NOT_FOUND)
                .entity(Map.of("message", message)).build();
    }
}
