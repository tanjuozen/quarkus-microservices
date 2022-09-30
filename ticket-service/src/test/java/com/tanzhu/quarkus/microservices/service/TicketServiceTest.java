package com.tanzhu.quarkus.microservices.service;

import com.tanzhu.quarkus.microservices.model.Ticket;
import com.tanzhu.quarkus.microservices.model.TicketStatus;
import com.tanzhu.quarkus.microservices.repository.TicketRepository;
import com.tanzhu.quarkus.microservices.util.TestData;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
@TestTransaction
class TicketServiceTest {

    @Inject
    TicketService ticketService;

    @Inject
    TicketRepository ticketRepository;


    @Test
    void bookTicketThatDoesntExist() {

        Ticket ticket = TestData.generateDummyTicketWith("orderId", "accountId", "testName", "1", new BigDecimal("60.15"));

        Ticket actual = ticketService.bookTicket(ticket);

        assertNotNull(actual);
        assertNotNull(actual.getId());
        assertNotNull(actual.getStatus());
        assertEquals(TicketStatus.TICKET_BOOKING_PENDING, actual.getStatus());
        assertEquals("orderId", actual.getOrderId());
        assertEquals("accountId", actual.getAccountId());
        assertEquals("testName", actual.getName());
        assertEquals("1", actual.getNumberOfPersons());
        assertEquals("60.15", actual.getCost().toString());
    }


    @Test
    void bookTicketThatIsBookingPending() {
        // given
        Ticket existing = TestData.generateDummyTicketWith("orderId", "accountId", TicketStatus.TICKET_BOOKING_PENDING, "testName", "1", new BigDecimal("60.15"));
        ticketRepository.persist(existing);
        // when
        Ticket newTicket = TestData.generateDummyTicketWith("orderId", "accountId", "testName", "1", new BigDecimal("60.15"));
        Ticket actual = ticketService.bookTicket(newTicket);

        // then
        assertNotNull(actual);
        assertNotNull(actual.getId());
        assertEquals(existing.getId(), actual.getId());
        assertNotNull(actual.getStatus());
        assertEquals(TicketStatus.TICKET_BOOKING_PENDING, actual.getStatus());
        assertEquals("orderId", actual.getOrderId());
        assertEquals("accountId", actual.getAccountId());
        assertEquals("testName", actual.getName());
        assertEquals("1", actual.getNumberOfPersons());
        assertEquals("60.15", actual.getCost().toString());
        assertNotNull(actual.getMessageSeverity());
        assertEquals("ERROR", actual.getMessageSeverity());
        assertNotNull(actual.getMessageOnTicket());
        assertEquals("Pending booking for accountId: accountId", actual.getMessageOnTicket());
    }

    @Test
    void bookTicketThatIsAlreadyBooked() {
        // given
        Ticket existing = TestData.generateDummyTicketWith("orderId", "accountId", TicketStatus.TICKET_BOOKING_APPROVED, "testName", "1", new BigDecimal("23.15"));
        ticketRepository.persist(existing);

        // when
        Ticket newTicket = TestData.generateDummyTicketWith("orderId", "accountId", "testName", "1", new BigDecimal("60.15"));
        Ticket actual = ticketService.bookTicket(newTicket);

        // then
        assertNotNull(actual);
        assertNotNull(actual.getId());
        assertEquals(existing.getId(), actual.getId());
        assertNotNull(actual.getStatus());
        assertEquals(TicketStatus.TICKET_BOOKING_APPROVED, actual.getStatus());
        assertEquals("orderId", actual.getOrderId());
        assertEquals("accountId", actual.getAccountId());
        assertEquals("testName", actual.getName());
        assertEquals("1", actual.getNumberOfPersons());
        assertEquals("23.15", actual.getCost().toString());
        assertNotNull(actual.getMessageSeverity());
        assertEquals("ERROR", actual.getMessageSeverity());
        assertNotNull(actual.getMessageOnTicket());
        assertEquals("Already booked for orderId: orderId", actual.getMessageOnTicket());
    }
}