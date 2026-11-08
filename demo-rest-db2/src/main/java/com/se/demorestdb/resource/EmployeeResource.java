package com.se.demorestdb.resource;

import com.se.demorestdb.model.Employee;
import com.se.demorestdb.service.EmployeeService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/employee")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EmployeeResource {

    @Inject
    private EmployeeService employeeService;

    @GET
    public Response findAll(){
        return Response.ok(employeeService.findAll()).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") int id){
        Employee result = employeeService.findById(id);
        return result == null
                ? Response.status(Response.Status.NOT_FOUND).build()
                : Response.ok(result).build();
    }

    @POST
    public Response insert(Employee employee){
        Employee result = employeeService.insert(employee);
        return result == null
                ? Response.status(Response.Status.BAD_REQUEST).entity("Ma employee khong tim thay").type(MediaType.TEXT_PLAIN).build()
                : Response.status(Response.Status.CREATED).entity(result).build();
    }

    @GET
    @Path("/avg-salary-by-dept")
    public Response avgSalaryByDept(){
        return Response.ok(employeeService.avgSalaryByDept()).build();
    }
}
