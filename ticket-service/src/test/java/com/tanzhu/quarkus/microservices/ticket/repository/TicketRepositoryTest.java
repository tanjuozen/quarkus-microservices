package com.tanzhu.quarkus.microservices.ticket.repository;

import com.tanzhu.quarkus.microservices.ticket.model.Ticket;
import com.tanzhu.quarkus.microservices.ticket.model.TicketStatus;
import com.tanzhu.quarkus.microservices.ticket.util.TestData;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
@TestTransaction
class TicketRepositoryTest {

    @Inject
    TicketRepository repository;

    @Test
    void testPersistingTicket() {
        Ticket ticket = TestData.generateDummyTicketWith("dummyOrderId", "dummyAccountId", TicketStatus.TICKET_BOOKING_PENDING, "dummyName");

        // when
        repository.persist(ticket);

        // then
        assertTrue(repository.isPersistent(ticket));
        assertNotNull(ticket.getId());
        assertEquals(1, repository.count());
    }


    @Test
    void findByAccountAndStatus() {
        Ticket ticket = TestData.generateDummyTicketWith("newOrderId", "newAccountId", TicketStatus.TICKET_BOOKING_PENDING, "newName");
        Ticket someTicket = TestData.generateDummyTicketWith("someOrderId", "someAccountId", TicketStatus.TICKET_PAYMENT_REJECTED, "someName");
        repository.persist(ticket);
        repository.persist(someTicket);

        // when
        Optional<Ticket> actual = repository.findByAccountAndStatus("newAccountId", TicketStatus.TICKET_BOOKING_PENDING);

        // then
        assertFalse(actual.isEmpty());
        assertEquals(2, repository.count());
        assertNotNull(actual.get().getId());
        assertEquals("newAccountId", actual.get().getAccountId());
        assertEquals(TicketStatus.TICKET_BOOKING_PENDING, actual.get().getStatus());
    }

    @Test
    void findByAccountAndStatus_NotFound() {
        Ticket ticket = TestData.generateDummyTicketWith("newOrderId", "newAccountId", TicketStatus.TICKET_BOOKING_PENDING, "newName");
        Ticket someTicket = TestData.generateDummyTicketWith("someOrderId", "someAccountId", TicketStatus.TICKET_PAYMENT_REJECTED, "someName");
        repository.persist(ticket);
        repository.persist(someTicket);

        // when
        Optional<Ticket> actual = repository.findByAccountAndStatus("wrongAccountId", TicketStatus.TICKET_BOOKING_PENDING);

        // then
        assertTrue(actual.isEmpty());
    }

    @Test
    void findByOrderIdAndState() {
        Ticket ticket = TestData.generateDummyTicketWith("newOrderId", "newAccountId", TicketStatus.TICKET_BOOKING_APPROVED, "newName");
        Ticket someTicket = TestData.generateDummyTicketWith("someOrderId", "someAccountId", TicketStatus.TICKET_PAYMENT_REJECTED, "someName");
        repository.persist(ticket);
        repository.persist(someTicket);

        // when
        Optional<Ticket> actual = repository.findByOrderAndStatus("newOrderId", TicketStatus.TICKET_BOOKING_APPROVED);

        // then
        assertFalse(actual.isEmpty());
        assertNotNull(actual.get().getId());
        assertEquals("newAccountId", actual.get().getAccountId());
        assertEquals(TicketStatus.TICKET_BOOKING_APPROVED, actual.get().getStatus());
        assertEquals(2, repository.count());
    }

    @Test
    void findByOrderAndState_NotFound() {
        Ticket ticket = TestData.generateDummyTicketWith("newOrderId", "newAccountId", TicketStatus.TICKET_BOOKING_APPROVED, "newName");
        Ticket someTicket = TestData.generateDummyTicketWith("someOrderId", "someAccountId", TicketStatus.TICKET_PAYMENT_REJECTED, "someName");
        repository.persist(ticket);
        repository.persist(someTicket);

        // when
        Optional<Ticket> actual = repository.findByOrderAndStatus("newOrderId", TicketStatus.TICKET_BOOKING_PENDING);

        // then
        assertTrue(actual.isEmpty());
    }
}