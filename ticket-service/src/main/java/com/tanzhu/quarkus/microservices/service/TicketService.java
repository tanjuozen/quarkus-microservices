package com.tanzhu.quarkus.microservices.service;


import com.tanzhu.quarkus.microservices.model.Ticket;
import com.tanzhu.quarkus.microservices.model.TicketEvent;
import com.tanzhu.quarkus.microservices.model.TicketEventType;
import com.tanzhu.quarkus.microservices.model.TicketStatus;
import com.tanzhu.quarkus.microservices.repository.TicketEventRepository;
import com.tanzhu.quarkus.microservices.repository.TicketRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Optional;

@ApplicationScoped
public class TicketService {

    private static final Logger log = LoggerFactory.getLogger(TicketService.class);
    private final TicketRepository ticketRepository;
    private final TicketEventRepository ticketEventRepository;

    public TicketService(TicketRepository ticketRepository, TicketEventRepository ticketEventRepository) {
        this.ticketRepository = ticketRepository;
        this.ticketEventRepository = ticketEventRepository;
    }

    @Transactional
    public Ticket bookTicket(Ticket ticket) {
        log.info("Booking a ticket for the order: " + ticket.getAccountId());

        Optional<Ticket> existing = ticketRepository.findByAccountAndStatus(ticket.getAccountId(), TicketStatus.TICKET_BOOKING_PENDING);
        if (existing.isPresent()) {
            log.error("A Pending booking with orderId: {} exists for account: {}", existing.get().getOrderId(), existing.get().getAccountId());
            existing.get().setMessageSeverity("ERROR");
            existing.get().setMessageOnTicket("Pending booking for accountId: " + existing.get().getAccountId());
            return existing.get();
        }

        existing = ticketRepository.findByOrderAndStatus(ticket.getOrderId(), TicketStatus.TICKET_BOOKING_APPROVED);
        if (existing.isPresent()) {
            log.error("orderId: {} already booked for account: {}", existing.get().getOrderId(), existing.get().getAccountId());
            existing.get().setMessageSeverity("ERROR");
            existing.get().setMessageOnTicket("Already booked for orderId: " + existing.get().getOrderId());
            return existing.get();
        }

        ticket.setStatus(TicketStatus.TICKET_BOOKING_PENDING);
        ticketRepository.persistAndFlush(ticket);

        TicketEvent ticketEvent = new TicketEvent();
        ticketEvent.setCorrelationId(ticket.getOrderId());
        ticketEvent.setAccountId(ticket.getAccountId());
        ticketEvent.setCreatedOn(Instant.now());
        ticketEvent.setItemId(ticket.getId());
        ticketEvent.setTotalCost(ticket.getCost());
        ticketEvent.setEvent(TicketEventType.TICKET_CREATED);

        ticketEventRepository.persistAndFlush(ticketEvent);
        
        return ticket;
    }
}
