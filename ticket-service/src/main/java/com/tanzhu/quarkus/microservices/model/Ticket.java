package com.tanzhu.quarkus.microservices.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(name = "OrderIdAndState", columnNames = {"orderId", "state"}))
public class Ticket extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    public Long id;

    @Column(nullable = false)
    public String orderId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    public TicketState state;

    @Column(nullable = false)
    public String accountId;

    public String name;

    public String numberOfPersons;

    public Double cost;
}
