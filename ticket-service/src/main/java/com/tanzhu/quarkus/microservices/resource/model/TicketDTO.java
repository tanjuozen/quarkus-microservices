package com.tanzhu.quarkus.microservices.resource.model;

import com.tanzhu.quarkus.microservices.model.TicketStatus;
import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class TicketDTO {
    private Long id;
    private String orderId;
    private TicketStatus status;
    private String accountId;
    private String name;
    private String numberOfPersons;
    private String cost;
    private String messageOnTicket;
    private String messageSeverity;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public void setStatus(TicketStatus status) {
        this.status = status;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumberOfPersons() {
        return numberOfPersons;
    }

    public void setNumberOfPersons(String numberOfPersons) {
        this.numberOfPersons = numberOfPersons;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getMessageOnTicket() {
        return messageOnTicket;
    }

    public void setMessageOnTicket(String messageOnTicket) {
        this.messageOnTicket = messageOnTicket;
    }

    public String getMessageSeverity() {
        return messageSeverity;
    }

    public void setMessageSeverity(String messageSeverity) {
        this.messageSeverity = messageSeverity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
