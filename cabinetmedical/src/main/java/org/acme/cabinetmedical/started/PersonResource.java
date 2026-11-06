package org.acme.cabinetmedical.started;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.eclipse.microprofile.jwt.JsonWebToken;

import java.net.URI;
import java.util.List;

@Path("/patients")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class PersonResource {

    @Inject
    JsonWebToken jwt; 

    @GET()
    @Path("/secured")
    @PermitAll 
    //@RolesAllowed({ "User", "Admin" })
    @Produces(MediaType.TEXT_PLAIN)
    public String hello(@Context SecurityContext ctx) {
        return getResponseString(ctx); 
    }

    private String getResponseString(SecurityContext ctx) {
        String name;
        if (ctx.getUserPrincipal() == null) { 
            name = "anonymous";
        } else if (!ctx.getUserPrincipal().getName().equals(jwt.getName())) { 
            throw new InternalServerErrorException("Principal and JsonWebToken names do not match");
        } else {
            name = ctx.getUserPrincipal().getName(); 
        }
        System.out.println( String.format(">> SecurityContext : + %s,"
            + " isHttps: %s,"
            + " authScheme: %s,"
            + " hasJWT: %s",
            name, ctx.isSecure(), ctx.getAuthenticationScheme(), hasJwt())); 
            
            return name;
    }

    private boolean hasJwt() {
	    return jwt.getClaimNames() != null;
    }
    
    @GET
    @RolesAllowed({ "Admin" })
    @Path("/all")
    public List<Patient> list() {
        return Patient.listAll();
    }

    @GET
    @RolesAllowed({ "User" })
    public List<Patient> listMyPatients(@Context SecurityContext ctx) {
        return Patient.findByDoctorName(getResponseString(ctx));
    }

    @GET
    @Path("/{id}")
    public Patient get(@PathParam("id") Long id) {
        return Patient.findById(id);
    }

 
    @POST
    @RolesAllowed({ "User" })
    @Transactional
    public Response create(Patient patient,@Context SecurityContext ctx) {
        patient.doctor = Medecin.findByLogin( getResponseString(ctx));
        patient.persist();
        return Response.created(URI.create("/patients/" + patient.id)).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Patient update(@PathParam("id") Long id, Patient p) {
        Patient entity = Patient.findById(id);
        if(entity == null) {
            throw new NotFoundException();
        }

        // map all fields from the person parameter to the existing entity
        entity.lname = p.lname;
        entity.fname = p.fname;
        entity.phone = p.phone;
        entity.sexe = p.sexe;

        entity.dossierMedical.affection = p.dossierMedical.affection;
        entity.dossierMedical.traitement = p.dossierMedical.traitement;

        return entity;
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public void delete(@PathParam("id") Long id) {
        Patient entity = Patient.findById(id);
        if(entity == null) {
            throw new NotFoundException();
        }
        entity.delete();
    }

    @GET
    @Path("/search/{name}")
    public Patient search(@PathParam("name") String name) {
        return Patient.findByName(name);
    }


    @GET
    @Path("/dead")
    public List<Patient> findDead() {
        return Patient.findNotAlive();
    }

    @GET
    @Path("/count")
    public Long count() {
        return Patient.count();
    }
}
