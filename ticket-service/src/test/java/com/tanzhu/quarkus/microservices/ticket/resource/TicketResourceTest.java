package com.tanzhu.quarkus.microservices.ticket.resource;

import com.tanzhu.quarkus.microservices.ticket.model.Ticket;
import com.tanzhu.quarkus.microservices.ticket.model.TicketStatus;
import com.tanzhu.quarkus.microservices.ticket.resource.model.TicketRequestDTO;
import com.tanzhu.quarkus.microservices.ticket.service.TicketService;
import com.tanzhu.quarkus.microservices.ticket.util.TestData;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.ArgumentMatchers.any;

@QuarkusTest
@TestHTTPEndpoint(TicketResource.class)
class TicketResourceTest {

    @InjectMock
    TicketService ticketService;

    @Test
    public void testHelloEndpoint() {
        TicketRequestDTO ticketRequestDTO = TestData.generateDummyTicketDTO("resource_orderId", "resource_accountId", "resource_testName", "3", "1");

        Mockito.when(ticketService.bookTicket(any(Ticket.class))).thenReturn(createdTicketFrom(ticketRequestDTO));


        given().contentType(MediaType.APPLICATION_JSON)
                .with().body(ticketRequestDTO)
                .when().post()
                .then()
                .statusCode(200)
                .body(containsString("{\"id\":1,\"orderId\":\"resource_orderId\",\"status\":\"TICKET_BOOKING_PENDING\",\"accountId\":\"resource_accountId\",\"name\":\"resource_testName\",\"numberOfPersons\":\"3\",\"cost\":1,\"messageOnTicket\":null,\"messageSeverity\":null}"));
    }

    private Ticket createdTicketFrom(TicketRequestDTO ticketRequestDTO) {
        Ticket ticket = new Ticket();
        ticket.setId(1L);
        ticket.setOrderId(ticketRequestDTO.getOrderId());
        ticket.setAccountId(ticketRequestDTO.getAccountId());
        ticket.setName(ticketRequestDTO.getName());
        ticket.setNumberOfPersons(ticketRequestDTO.getNumberOfPersons());
        ticket.setCost(new BigDecimal(ticketRequestDTO.getCost()));
        ticket.setStatus(TicketStatus.TICKET_BOOKING_PENDING);
        return ticket;
    }

}