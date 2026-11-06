
package org.acme.cabinetmedical.started;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


import java.net.URI;
import java.util.List;

@Path("/dossiers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DossierResource{

    @GET
    public List<DossierMedical> list() {
        return DossierMedical.listAll();
    }

    @GET
    @Path("/{id}")
    public DossierMedical get(@PathParam("id") Long id) {
        return DossierMedical.findById(id);
    }

    @GET
    @Path("/doctors")
    public List<Medecin> getDoctors() {
        return Medecin.listAll();
    }

    @POST
    @Path("/doctors")
    @Transactional
    public Response createDoctor(Medecin medecin) {
        
        medecin.persist();
        return Response.created(URI.create("/doctors/" + medecin.id)).build();
    }


}