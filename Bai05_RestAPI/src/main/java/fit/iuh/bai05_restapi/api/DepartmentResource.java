package fit.iuh.bai05_restapi.api;

import fit.iuh.bai05_restapi.dao.DepartmentDao;
import fit.iuh.bai05_restapi.model.Department;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/departments")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DepartmentResource {
    private DepartmentDao departmentDao = new DepartmentDao();

    @GET
    public Response findAll(@QueryParam("name") String name){
        if (name != null && !name.isBlank()){
            return Response.ok(departmentDao.findByName(name)).build();
        }
        return Response.ok(departmentDao.findAll()).build();
    }

    @POST
    public Response create(Department department){
       if (department == null || department.getName() == null){
           return Response.status(Response.Status.BAD_REQUEST).build();
       }

       department.setId(null);
       department.setEmployees(null);
       departmentDao.save(department);

       return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, Department department){

        if (department == null){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        if (departmentDao.findById(id) == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        departmentDao.update(id, department);
        return Response.ok(departmentDao.findById(id)).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id){
        if (departmentDao.findById(id) == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        departmentDao.delete(id);
        return Response.noContent().build();
    }
}
