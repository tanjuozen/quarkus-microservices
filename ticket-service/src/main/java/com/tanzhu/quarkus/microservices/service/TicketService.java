package com.tanzhu.quarkus.microservices.service;


import com.tanzhu.quarkus.microservices.model.Ticket;
import com.tanzhu.quarkus.microservices.model.TicketState;
import com.tanzhu.quarkus.microservices.repository.TicketRepository;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class TicketService {

    private static final Logger log = Logger.getLogger(TicketService.class);
    private final TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Transactional
    public void createTicket(Ticket ticket) {
        log.info("Creating a ticket with orderID: " + ticket.orderId);
        ticketRepository.persist(ticket);
    }

    public List<Ticket> listAllTickets() {
        return ticketRepository.listAll();
    }

    public List<Ticket> listPendingTicketsByAccount(Ticket ticket) {
        return ticketRepository.findByAccountAndState(ticket.accountId, TicketState.TICKET_BOOKED_PENDING);
    }

    public List<Ticket> listBookedTicketsByOrder(Ticket ticket) {
        return ticketRepository.findByOrderIdAndState(ticket.orderId, TicketState.TICKET_BOOKED);
    }
}
