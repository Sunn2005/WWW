package fit.iuh.bai6_restapi.api;

import fit.iuh.bai6_restapi.dao.DanhMucDao;
import fit.iuh.bai6_restapi.model.DanhMuc;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/danhmucs")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DanhMucResource {
    private DanhMucDao danhMucDao = new DanhMucDao();

    @GET
    public Response findAll(){
        return Response.ok(danhMucDao.findAll()).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(DanhMuc danhMuc){
        if (danhMuc == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        danhMuc.setId(null);
        danhMuc.setTinTucs(null);
        danhMucDao.create(danhMuc);
        return Response.status(Response.Status.CREATED).entity(danhMuc).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") Long id, DanhMuc danhMuc){
        if (danhMuc == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        if (danhMucDao.findById(id) == null){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        danhMucDao.update(id, danhMuc);
        return Response.ok(danhMucDao.findById(id)).entity(danhMuc).build();
    }
}
