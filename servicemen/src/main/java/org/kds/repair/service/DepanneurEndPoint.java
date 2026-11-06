package org.kds.repair.service;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.logging.Logger;
import java.net.URI;
import java.util.List;

@Path("/servicemen")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DepanneurEndPoint {

    @Inject
    Logger log;  

    @GET
    public List<Depanneur> list() {
        return Depanneur.listAll();
    }


    @POST
    @Transactional
    public Response create(Depanneur repairman) {
        log.info("==== in create repairman : "+repairman.toString());
        repairman.persist();
        return Response.created(URI.create("/serviceman/" + repairman.id)).build();
    }
}
