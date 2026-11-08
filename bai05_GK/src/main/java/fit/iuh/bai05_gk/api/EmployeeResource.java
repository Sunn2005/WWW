package fit.iuh.bai05_gk.api;

import fit.iuh.bai05_gk.dao.DepartmentDao;
import fit.iuh.bai05_gk.dao.EmployeeDao;
import fit.iuh.bai05_gk.model.Department;
import fit.iuh.bai05_gk.model.Employee;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Map;

@Path("/employees")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EmployeeResource {

    private final EmployeeDao employeeDao = new EmployeeDao();
    private final DepartmentDao departmentDao = new DepartmentDao();

    @GET
    public Response findAll() {
        return Response.ok(employeeDao.findAll()).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        Employee employee = employeeDao.findById(id);
        if (employee == null) {
            return notFound("Employee không tồn tại");
        }
        return Response.ok(employee).build();
    }

    @POST
    public Response create(Employee employee) {
        Response error = validate(employee);
        if (error != null) return error;

        Department department = departmentDao.findById(employee.getDepartment().getId());
        if (department == null) {
            return badRequest("Department không tồn tại");
        }

        employee.setId(null);
        employee.setDepartment(department);
        employeeDao.save(employee);
        return Response.status(Response.Status.CREATED).entity(employee).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, Employee employee) {
        if (employeeDao.findById(id) == null) {
            return notFound("Employee không tồn tại");
        }

        Response error = validate(employee);
        if (error != null) return error;

        Department department = departmentDao.findById(employee.getDepartment().getId());
        if (department == null) {
            return badRequest("Department không tồn tại");
        }

        employee.setDepartment(department);
        employeeDao.update(id, employee);
        return Response.ok(employeeDao.findById(id)).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        if (employeeDao.findById(id) == null) {
            return notFound("Employee không tồn tại");
        }
        employeeDao.delete(id);
        return Response.noContent().build();
    }

    private Response validate(Employee employee) {
        if (employee == null || employee.getName() == null
                || employee.getName().isBlank()) {
            return badRequest("Tên employee không được để trống");
        }
        if (employee.getSalary() < 0) {
            return badRequest("Salary không được âm");
        }
        if (employee.getDepartment() == null
                || employee.getDepartment().getId() == null) {
            return badRequest("Thiếu department.id");
        }
        return null;
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
