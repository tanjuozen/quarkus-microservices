package com.tanzhu.quarkus.microservices.util;

import com.tanzhu.quarkus.microservices.model.Ticket;
import com.tanzhu.quarkus.microservices.model.TicketStatus;
import com.tanzhu.quarkus.microservices.resource.model.TicketDTO;

import java.math.BigDecimal;

public class TestData {

    public static Ticket generateDummyTicketWith(String orderId, String accountId, TicketStatus status, String name, String numberOfPersons, BigDecimal cost) {
        Ticket ticket = new Ticket();
        ticket.setOrderId(orderId);
        ticket.setAccountId(accountId);
        ticket.setStatus(status);
        ticket.setName(name);
        ticket.setNumberOfPersons(numberOfPersons);
        ticket.setCost(cost);
        return ticket;
    }
    public static Ticket generateDummyTicketWith(String orderId, String accountId, String name, String numberOfPersons, BigDecimal cost) {
        Ticket ticket = new Ticket();
        ticket.setOrderId(orderId);
        ticket.setAccountId(accountId);
        ticket.setName(name);
        ticket.setNumberOfPersons(numberOfPersons);
        ticket.setCost(cost);
        return ticket;
    }
    public static Ticket generateDummyTicketWith(String orderId, String accountId, TicketStatus status, String name) {
        Ticket ticket = new Ticket();
        ticket.setOrderId(orderId);
        ticket.setAccountId(accountId);
        ticket.setStatus(status);
        ticket.setName(name);
        return ticket;
    }
    public static TicketDTO generateDummyTicketDTO(String orderId, String accountId, String name, String numberOfPersons, String cost) {
        TicketDTO ticket = new TicketDTO();
        ticket.setOrderId(orderId);
        ticket.setAccountId(accountId);
        ticket.setName(name);
        ticket.setNumberOfPersons(numberOfPersons);
        ticket.setCost(cost);
        return ticket;
    }

}
