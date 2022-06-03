package com.tanzhu.quarkus.microservices.repository;

import com.tanzhu.quarkus.microservices.model.TicketEvent;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TicketEventRepository implements PanacheRepository<TicketEvent> {
}
