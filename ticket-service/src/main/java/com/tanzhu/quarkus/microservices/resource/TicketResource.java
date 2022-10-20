package com.tanzhu.quarkus.microservices.resource;

import com.tanzhu.quarkus.microservices.model.Ticket;
import com.tanzhu.quarkus.microservices.resource.model.ErrorDTO;
import com.tanzhu.quarkus.microservices.resource.model.TicketRequestDTO;
import com.tanzhu.quarkus.microservices.resource.model.TicketResponseDTO;
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
    public Response create(TicketRequestDTO ticketRequestDTO) {
        Ticket ticket = mapToTicket(ticketRequestDTO);
        ticket = ticketService.bookTicket(ticket);

        TicketResponseDTO responseDTO = mapToResponse(ticket);


        if(responseDTO.getMessageSeverity() != null && responseDTO.getMessageSeverity().equals("ERROR")) {
            ErrorDTO errorMessage = new ErrorDTO();
            errorMessage.setMessage(responseDTO.getMessageOnTicket());
            errorMessage.setSeverity(responseDTO.getMessageSeverity());
            return Response.status(500).entity(errorMessage).build();
        }

        return Response.ok(responseDTO).build();
    }



    private Ticket mapToTicket(TicketRequestDTO ticketRequestDTO) {
        Ticket ticket = new Ticket();
        ticket.setAccountId(ticketRequestDTO.getAccountId());
        ticket.setOrderId(ticketRequestDTO.getOrderId());
        ticket.setName(ticketRequestDTO.getName());
        ticket.setNumberOfPersons(ticketRequestDTO.getNumberOfPersons());
        ticket.setCost(new BigDecimal(ticketRequestDTO.getCost()));
        return ticket;
    }

    private TicketResponseDTO mapToResponse(Ticket ticket) {
        TicketResponseDTO responseDTO = new TicketResponseDTO();
        responseDTO.setId(ticket.getId());
        responseDTO.setOrderId(ticket.getOrderId());
        responseDTO.setAccountId(ticket.getAccountId());
        responseDTO.setName(ticket.getName());
        responseDTO.setNumberOfPersons(ticket.getNumberOfPersons());
        responseDTO.setCost(ticket.getCost());
        responseDTO.setStatus(ticket.getStatus());
        responseDTO.setMessageSeverity(ticket.getMessageSeverity());
        responseDTO.setMessageOnTicket(ticket.getMessageOnTicket());
        return responseDTO;
    }
}
