package com.tanzhu.quarkus.microservices.repository;

import com.tanzhu.quarkus.microservices.model.TicketEvent;
import com.tanzhu.quarkus.microservices.model.TicketEventType;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
@TestTransaction
class TicketEventRepositoryTest {

    @Inject
    TicketEventRepository eventRepository;

    @Test
    void testPersistingTicketEvent() {
        // given
        TicketEvent ticketEvent = new TicketEvent();
        ticketEvent.setCorrelationId("dummyOrderId");
        ticketEvent.setItemId(1L);
        ticketEvent.setAccountId("dummyAccountId");
        ticketEvent.setEvent(TicketEventType.TICKET_CREATED);
        ticketEvent.setTotalCost(new BigDecimal("100.50"));

        // when
        eventRepository.persist(ticketEvent);

        assertTrue(eventRepository.isPersistent(ticketEvent));
        assertNotNull(ticketEvent.getId());
        assertNotNull(eventRepository.findById(1L));
        assertEquals("dummyOrderId", eventRepository.findById(1L).getCorrelationId());
        assertEquals("dummyAccountId", eventRepository.findById(1L).getAccountId());
        assertEquals(TicketEventType.TICKET_CREATED, eventRepository.findById(1L).getEvent());
        assertEquals(0, eventRepository.findById(1L).getTotalCost().compareTo(new BigDecimal("100.50")));
        assertEquals(2, eventRepository.findById(1L).getTotalCost().scale());
        assertEquals(5, eventRepository.findById(1L).getTotalCost().precision());
    }
}