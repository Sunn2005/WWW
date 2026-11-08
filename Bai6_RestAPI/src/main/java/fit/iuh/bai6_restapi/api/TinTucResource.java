package fit.iuh.bai6_restapi.api;

import fit.iuh.bai6_restapi.dao.DanhMucDao;
import fit.iuh.bai6_restapi.dao.TinTucDao;
import fit.iuh.bai6_restapi.model.DanhMuc;
import fit.iuh.bai6_restapi.model.TinTuc;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/tintucs")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TinTucResource {
    private TinTucDao tinTucDao = new TinTucDao();

    @GET
    public Response findAll(){
        return Response.ok(tinTucDao.findAll()).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(TinTuc tinTuc){
        if (tinTuc == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        tinTuc.setId(null);
        tinTucDao.create(tinTuc);
        return Response.status(Response.Status.CREATED).entity(tinTuc).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") Long id, TinTuc tinTuc){
        if (tinTuc == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        if (tinTucDao.findById(id) == null){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        tinTucDao.update(id, tinTuc);
        return Response.ok(tinTucDao.findById(id)).entity(tinTuc).build();
    }
}
