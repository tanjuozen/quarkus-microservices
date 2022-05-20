package com.tanzhu.quarkus.microservices;

import com.tanzhu.quarkus.microservices.model.Ticket;
import com.tanzhu.quarkus.microservices.service.TicketService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/tickets")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ExampleResource {


    private final TicketService ticketService;

    public ExampleResource(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GET
    public List<Ticket> getAll() {
        return ticketService.listAllTickets();
    }

    @POST
    public Response createTicket(Ticket ticket) {
        ticketService.createTicket(ticket);
        return Response.status(Response.Status.CREATED).build();
    }
}