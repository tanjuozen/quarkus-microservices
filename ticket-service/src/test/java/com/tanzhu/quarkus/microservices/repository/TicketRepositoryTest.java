package com.tanzhu.quarkus.microservices.repository;

import com.tanzhu.quarkus.microservices.model.Ticket;
import com.tanzhu.quarkus.microservices.model.TicketStatus;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.persistence.NoResultException;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
@TestTransaction
class TicketRepositoryTest {

    @Inject
    TicketRepository repository;

    @Test
    void testPersistingTicket() {
        Ticket ticket = generateDummyTicketWith("dummyOrderId", "dummyAccountId", TicketStatus.TICKET_BOOKING_PENDING, "dummyName");

        // when
        repository.persist(ticket);

        // then
        assertTrue(repository.isPersistent(ticket));
        assertNotNull(ticket.id);
        assertEquals(1, repository.count());
    }


    @Test
    void findByAccountAndStatus() {
        Ticket ticket = generateDummyTicketWith("newOrderId", "newAccountId", TicketStatus.TICKET_BOOKING_PENDING, "newName");
        Ticket someTicket = generateDummyTicketWith("someOrderId", "someAccountId", TicketStatus.TICKET_PAYMENT_REJECTED, "someName");
        repository.persist(ticket);
        repository.persist(someTicket);

        // when
        Ticket actual = repository.findByAccountAndStatus("newAccountId", TicketStatus.TICKET_BOOKING_PENDING);

        // then
        assertEquals(2, repository.count());
        assertNotNull(actual.id);
        assertEquals("newAccountId", actual.accountId);
        assertEquals(TicketStatus.TICKET_BOOKING_PENDING, actual.status);
    }

    @Test
    void findByAccountAndStatus_NotFound() {
        Ticket ticket = generateDummyTicketWith("newOrderId", "newAccountId", TicketStatus.TICKET_BOOKING_PENDING, "newName");
        Ticket someTicket = generateDummyTicketWith("someOrderId", "someAccountId", TicketStatus.TICKET_PAYMENT_REJECTED, "someName");
        repository.persist(ticket);
        repository.persist(someTicket);

        // when and then
        Assertions.assertThrows(NoResultException.class,
                () -> repository.findByAccountAndStatus("wrongAccountId", TicketStatus.TICKET_BOOKING_PENDING));
    }

    @Test
    void findByOrderIdAndState() {
        Ticket ticket = generateDummyTicketWith("newOrderId", "newAccountId", TicketStatus.TICKET_BOOKING_APPROVED, "newName");
        Ticket someTicket = generateDummyTicketWith("someOrderId", "someAccountId", TicketStatus.TICKET_PAYMENT_REJECTED, "someName");
        repository.persist(ticket);
        repository.persist(someTicket);

        // when
        Ticket actual = repository.findByOrderIdAndState("newOrderId", TicketStatus.TICKET_BOOKING_APPROVED);

        // then
        assertNotNull(actual.id);
        assertEquals("newAccountId", actual.accountId);
        assertEquals(TicketStatus.TICKET_BOOKING_APPROVED, actual.status);
        assertEquals(2, repository.count());
    }

    @Test
    void findByOrderIdAndState_NotFound() {
        Ticket ticket = generateDummyTicketWith("newOrderId", "newAccountId", TicketStatus.TICKET_BOOKING_APPROVED, "newName");
        Ticket someTicket = generateDummyTicketWith("someOrderId", "someAccountId", TicketStatus.TICKET_PAYMENT_REJECTED, "someName");
        repository.persist(ticket);
        repository.persist(someTicket);

        // when and then
        Assertions.assertThrows(NoResultException.class,
                () -> repository.findByOrderIdAndState("newOrderId", TicketStatus.TICKET_BOOKING_PENDING));
    }

    private Ticket generateDummyTicketWith(String orderId, String accountId, TicketStatus status, String name) {
        Ticket ticket = new Ticket();
        ticket.orderId = orderId;
        ticket.accountId = accountId;
        ticket.status = status;
        ticket.name = name;
        return ticket;
    }
}