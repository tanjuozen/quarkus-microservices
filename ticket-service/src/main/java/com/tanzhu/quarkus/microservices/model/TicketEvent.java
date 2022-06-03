package com.tanzhu.quarkus.microservices.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(name = "CorrelationIdAndEvent", columnNames = {"correlationId", "event"}))
public class TicketEvent  extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    public Long id;

    @Column(nullable = false)
    public String correlationId;

    @Column(nullable = false)
    public Long itemId;

    @Column(nullable = false)
    public String accountId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    public TicketEventType event;

    @Column(nullable = false)
    public BigDecimal totalCost;

    public Instant createdOn;
}
