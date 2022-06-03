package com.tanzhu.quarkus.microservices.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(name = "OrderIdAndStatus", columnNames = {"orderId", "status"}))
public class Ticket extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    public Long id;

    @Column(nullable = false)
    public String orderId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    public TicketStatus status;

    @Column(nullable = false)
    public String accountId;

    public String name;

    public String numberOfPersons;

    public BigDecimal cost;

    @Transient
    public String messageOnTicket;

    @Transient
    public String messageSeverity;
}
