package com.tanzhu.quarkus.microservices.repository;

import com.tanzhu.quarkus.microservices.model.Ticket;
import com.tanzhu.quarkus.microservices.model.TicketStatus;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TicketRepository implements PanacheRepository<Ticket> {

    public Ticket findByOrderAndState(String orderId, TicketStatus status) {
        return find("SELECT t FROM Ticket t where t.orderId = ?1 and t.status = ?2", orderId, status).singleResult();
    }

    public Ticket findByAccountAndStatus(String accountId, TicketStatus status) {
        return find("SELECT t FROM Ticket t where t.accountId = ?1 and t.status = ?2", accountId, status).singleResult();
    }

}
