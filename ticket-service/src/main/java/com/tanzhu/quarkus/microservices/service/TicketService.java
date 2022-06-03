package com.tanzhu.quarkus.microservices.service;


import com.tanzhu.quarkus.microservices.model.Ticket;
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
    public void bookTicket(Ticket ticket) {
        log.info("Booking a ticket for the order: " + ticket.orderId);
    }
}
