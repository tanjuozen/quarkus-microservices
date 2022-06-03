package com.tanzhu.quarkus.microservices.healthcheck;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;

import javax.enterprise.context.ApplicationScoped;
import java.util.Date;

@Liveness
@ApplicationScoped
public class TicketServiceLivenessCheck implements HealthCheck {

    @Override
    public HealthCheckResponse call() {
        return HealthCheckResponse.named("ticket service is alive")
                .withData("time", String.valueOf(new Date()))
                .up()
                .build();
    }
}