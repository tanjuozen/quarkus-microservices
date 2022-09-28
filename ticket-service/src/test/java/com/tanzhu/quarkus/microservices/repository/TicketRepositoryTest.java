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
        assertNotNull(ticket.getId());
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
        assertNotNull(actual.getId());
        assertEquals("newAccountId", actual.getAccountId());
        assertEquals(TicketStatus.TICKET_BOOKING_PENDING, actual.getStatus());
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
        Ticket actual = repository.findByOrderAndState("newOrderId", TicketStatus.TICKET_BOOKING_APPROVED);

        // then
        assertNotNull(actual.getId());
        assertEquals("newAccountId", actual.getAccountId());
        assertEquals(TicketStatus.TICKET_BOOKING_APPROVED, actual.getStatus());
        assertEquals(2, repository.count());
    }

    @Test
    void findByOrderAndState_NotFound() {
        Ticket ticket = generateDummyTicketWith("newOrderId", "newAccountId", TicketStatus.TICKET_BOOKING_APPROVED, "newName");
        Ticket someTicket = generateDummyTicketWith("someOrderId", "someAccountId", TicketStatus.TICKET_PAYMENT_REJECTED, "someName");
        repository.persist(ticket);
        repository.persist(someTicket);

        // when and then
        Assertions.assertThrows(NoResultException.class,
                () -> repository.findByOrderAndState("newOrderId", TicketStatus.TICKET_BOOKING_PENDING));
    }

    private Ticket generateDummyTicketWith(String orderId, String accountId, TicketStatus status, String name) {
        Ticket ticket = new Ticket();
        ticket.setOrderId(orderId);
        ticket.setAccountId(accountId);
        ticket.setStatus(status);
        ticket.setName(name);
        return ticket;
    }
}