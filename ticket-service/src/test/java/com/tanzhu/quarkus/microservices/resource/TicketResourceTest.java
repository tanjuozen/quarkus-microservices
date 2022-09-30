package com.tanzhu.quarkus.microservices.resource;

import com.tanzhu.quarkus.microservices.resource.model.TicketDTO;
import com.tanzhu.quarkus.microservices.util.TestData;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.MediaType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
@TestTransaction
@TestHTTPEndpoint(TicketResource.class)
@Disabled
class TicketResourceTest {

    @Test
    @TestTransaction
    public void testHelloEndpoint() {
        TicketDTO ticketDTO = TestData.generateDummyTicketDTO("orderId", "accountId", "testName", "1", "60");
        given().contentType(MediaType.APPLICATION_JSON)
                .with().body(ticketDTO)
                .when().post()
                .then()
                .statusCode(200)
                .body(is("Hello tanzhu"));
    }
}