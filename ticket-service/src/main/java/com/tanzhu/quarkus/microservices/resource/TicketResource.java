package com.tanzhu.quarkus.microservices.resource;

import com.tanzhu.quarkus.microservices.model.Ticket;
import com.tanzhu.quarkus.microservices.resource.model.ErrorDTO;
import com.tanzhu.quarkus.microservices.resource.model.TicketDTO;
import com.tanzhu.quarkus.microservices.service.TicketService;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;

@Path("/tickets")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TicketResource {
    
    @Inject
    TicketService ticketService;
    
    @POST
    public Response create(TicketDTO ticketDTO) {
        Ticket ticket = mapToTicket(ticketDTO);
        ticket = ticketService.bookTicket(ticket);

        ticketDTO.setId(ticket.getId());
        ticketDTO.setMessageOnTicket(ticket.getMessageOnTicket());
        ticketDTO.setMessageSeverity(ticket.getMessageSeverity());


        if(ticketDTO.getMessageSeverity() != null && ticketDTO.getMessageSeverity().equals("ERROR")) {
            ErrorDTO errorMessage = new ErrorDTO();
            errorMessage.setMessage(ticketDTO.getMessageOnTicket());
            errorMessage.setSeverity(ticketDTO.getMessageSeverity());
            return Response.status(500).entity(errorMessage).build();
        }

        return Response.ok(ticketDTO).build();
    }

    private Ticket mapToTicket(TicketDTO ticketDTO) {
        Ticket ticket = new Ticket();
        ticket.setAccountId(ticketDTO.getAccountId());
        ticket.setOrderId(ticketDTO.getOrderId());
        ticket.setName(ticketDTO.getName());
        ticket.setNumberOfPersons(ticketDTO.getNumberOfPersons());
        ticket.setCost(new BigDecimal(ticketDTO.getCost()));
        return ticket;
    }
}
