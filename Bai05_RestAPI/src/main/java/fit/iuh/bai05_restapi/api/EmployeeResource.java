package fit.iuh.bai05_restapi.api;

import fit.iuh.bai05_restapi.dao.DepartmentDao;
import fit.iuh.bai05_restapi.dao.EmployeeDao;
import fit.iuh.bai05_restapi.model.Employee;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/employees")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EmployeeResource {
    private EmployeeDao employeeDao = new EmployeeDao();


    @GET
    public Response findAll(){
        return Response.ok(employeeDao.findAll()).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id){
        if (employeeDao.findById(id) == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(employeeDao.findById(id)).build();
    }

    @POST
    public Response create(Employee employee){
        if (employee == null ) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        employee.setId(null);

        employeeDao.save(employee);
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, Employee employee){
        if (employee == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        if (employeeDao.findById(id) == null){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        employeeDao.update(id, employee);
        return Response.ok(employeeDao.findById(id)).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id){
        if (employeeDao.findById(id) == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        employeeDao.delete(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
