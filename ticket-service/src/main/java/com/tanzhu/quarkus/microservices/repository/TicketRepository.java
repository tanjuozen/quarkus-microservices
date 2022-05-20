package com.tanzhu.quarkus.microservices.repository;

import com.tanzhu.quarkus.microservices.model.Ticket;
import com.tanzhu.quarkus.microservices.model.TicketState;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class TicketRepository implements PanacheRepository<Ticket> {

    public List<Ticket> findByAccountAndState(String accountId, TicketState state) {
        return find("SELECT t FROM Ticket t where t.accountId = :? and t.state = :?", accountId, state).list();
    }

    public List<Ticket> findByOrderIdAndState(String orderId, TicketState state) {
        return find("SELECT t FROM Ticket t where t.orderId = :? and t.state = :?", orderId, state).list();
    }
}
