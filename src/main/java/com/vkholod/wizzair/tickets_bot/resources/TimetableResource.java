package com.vkholod.wizzair.tickets_bot.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/timetable")
@Produces(MediaType.APPLICATION_JSON)
public class TimetableResource {


    @GET
    public String sayHello() {
        return "TIMETABLE RESOURCE";
    }

}
