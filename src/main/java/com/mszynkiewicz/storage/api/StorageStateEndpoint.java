package com.mszynkiewicz.storage.api;

import com.mszynkiewicz.storage.service.StateCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Author: Michal Szynkiewicz, michal.l.szynkiewicz@gmail.com
 * Date: 11/5/15
 * Time: 10:43 AM
 */
@Controller
@Path("/state")
@Produces(MediaType.APPLICATION_JSON)
public class StorageStateEndpoint {

    @Autowired
    private StateCache stateCache;

    @GET
    public Response getState() {
        return Response.ok(stateCache.getState()).build();
    }
}
