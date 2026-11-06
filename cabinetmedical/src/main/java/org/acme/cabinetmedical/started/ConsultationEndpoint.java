package org.acme.cabinetmedical.started;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

@Path("/consultation")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ConsultationEndpoint {
    
    @GET
    public List<Consultation> list() {
        return Consultation.listAll();
    }

    @GET
    @Path("/{id}")
    public Consultation get(@PathParam("id") Long id) {
        return Consultation.findById(id);
    }

 
    @POST
    @Transactional
    public Response create(Consultation consult) {
        consult.persist();
        return Response.created(URI.create("/consultation/" + consult.id)).build();
    }

    @GET
    @Path("/doctors")
    public List<Medecin> listMedecins() {
        return Medecin.listAll();
    }

    @POST
    @Path("/addDoctor")
    @Transactional
    public List<Medecin> createDoctor(Medecin doctor) {
        doctor.persist();
        return Medecin.listAll();
        
    }
    @GET
    @Path("/actes")
    public List<Actes> listActes() {
        return Actes.listAll();
    }
}
