package org.kds.repair.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/helloservicemen")
public class RepairServiceResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String servicemenList() {
        return "Hello from repair servicemen";
    }
}