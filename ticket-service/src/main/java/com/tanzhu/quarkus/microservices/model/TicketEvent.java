package com.tanzhu.quarkus.microservices.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.debezium.outbox.quarkus.ExportedEvent;

import java.time.Instant;

public class TicketEvent implements ExportedEvent<String, JsonNode> {

    private static ObjectMapper mapper = new ObjectMapper();
    private final String correlationId;
    private final JsonNode ticketEvent;
    private final Instant createdOn;

    private TicketEvent(String correlationId, JsonNode ticketEvent) {
        this.correlationId = correlationId;
        this.createdOn = Instant.now();
        this.ticketEvent = ticketEvent;
    }
    public static TicketEvent of(Ticket ticket) {
        ObjectNode jsonTicketEvent = mapper.createObjectNode()
                .put("accountId", ticket.getAccountId())
                .put("itemId", ticket.getId())
                .put("totalCost", ticket.getCost());
        return new TicketEvent(ticket.getOrderId(), jsonTicketEvent);
    }

    @Override
    public String getAggregateId() {
        return correlationId;
    }

    @Override
    public String getAggregateType() {
        return "Ticket";
    }

    @Override
    public String getType() {
        return TicketEventType.TICKET_CREATED.name();
    }

    @Override
    public Instant getTimestamp() {
        return createdOn;
    }

    @Override
    public JsonNode getPayload() {
        return ticketEvent;
    }
}
