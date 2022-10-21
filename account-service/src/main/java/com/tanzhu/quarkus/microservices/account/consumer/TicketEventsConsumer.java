package com.tanzhu.quarkus.microservices.account.consumer;

import com.tanzhu.quarkus.microservices.account.exception.TicketsEventException;
import io.opentracing.Scope;
import io.opentracing.Tracer;
import io.opentracing.contrib.kafka.TracingKafkaUtils;
import io.smallrye.reactive.messaging.kafka.KafkaRecord;
import org.apache.kafka.common.header.Header;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletionStage;


@ApplicationScoped
public class TicketEventsConsumer {
    private static final Logger log = LoggerFactory.getLogger(TicketEventsConsumer.class);

    @Inject
    Tracer tracer;

    @Incoming("tickets")
    public CompletionStage<Void> onTicketEvents(KafkaRecord<String, String> message) {
        final String eventType = getHeaderAsString(message, "EventType");
        final String correlationId = getHeaderAsString(message, "id");

        Tracer.SpanBuilder spanBuilder = tracer.buildSpan("tickets").asChildOf(TracingKafkaUtils.extractSpanContext(message.getHeaders(), tracer));
        try (final Scope span = tracer.scopeManager().activate(spanBuilder.start())) {
            log.info("Kafka mesage with key: {} received", message.getKey());
            log.info("Ticket event received with payload: {}", message.getPayload());
            message.getHeaders().forEach(header -> {
                log.info("Header Record key: {}, value:{}",header.key(), getHeaderAsString(message, header.key()));
            });
        } catch (Exception e) {
            throw new TicketsEventException("Error while processing Ticket event", e);
        }
        return message.ack();
    }

    private String getHeaderAsString(KafkaRecord<?, ?> record, String name) {
        Header header = record.getHeaders().lastHeader(name);
        if (header == null || header.value().length == 0) {
            throw new TicketsEventException("Expected record header '" + name + "' not present");
        }

        return new String(header.value(), StandardCharsets.UTF_8);
    }
}
