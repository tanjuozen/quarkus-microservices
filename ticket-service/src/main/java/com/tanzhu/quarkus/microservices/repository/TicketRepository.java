package com.tanzhu.quarkus.microservices.repository;

import com.tanzhu.quarkus.microservices.model.Ticket;
import com.tanzhu.quarkus.microservices.model.TicketStatus;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.NoResultException;
import java.util.Optional;

@ApplicationScoped
public class TicketRepository implements PanacheRepository<Ticket> {

    public Optional<Ticket> findByOrderAndStatus(String orderId, TicketStatus status) {
        try {
            return find("#Ticket.findByOrderAndStatus", orderId, status).stream().findFirst();
        } catch (NoResultException exception) {
            return Optional.empty();
        }
    }

    public Optional<Ticket> findByAccountAndStatus(String accountId, TicketStatus status) {
        try {
            return find("#Ticket.findByAccountAndStatus", accountId, status).stream().findFirst();
        } catch (NoResultException exception) {
            return Optional.empty();
        }
    }
}
