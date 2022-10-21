package com.tanzhu.quarkus.microservices.ticket.service;


import com.tanzhu.quarkus.microservices.ticket.model.Ticket;
import com.tanzhu.quarkus.microservices.ticket.model.TicketCreatedEvent;
import com.tanzhu.quarkus.microservices.ticket.model.TicketStatus;
import com.tanzhu.quarkus.microservices.ticket.repository.TicketRepository;
import io.debezium.outbox.quarkus.ExportedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.transaction.Transactional;
import java.util.Optional;

@ApplicationScoped
public class TicketService {

    private static final Logger log = LoggerFactory.getLogger(TicketService.class);
    private final TicketRepository ticketRepository;
    private final Event<ExportedEvent<?,?>> ticketEventEmitter;

    public TicketService(TicketRepository ticketRepository, Event<ExportedEvent<?,?>> ticketEventEmitter) {
        this.ticketRepository = ticketRepository;
        this.ticketEventEmitter = ticketEventEmitter;
    }

    @Transactional
    public Ticket bookTicket(Ticket ticket) {
        log.info("Booking a ticket for the order: " + ticket.getOrderId());

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
        ticketEventEmitter.fire(TicketCreatedEvent.of(ticket));
        return ticket;
    }
}
