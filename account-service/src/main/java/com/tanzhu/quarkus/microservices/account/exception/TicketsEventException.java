package com.tanzhu.quarkus.microservices.account.exception;

public class TicketsEventException extends RuntimeException {
    public TicketsEventException(String message) {
        super(message);
    }

    public TicketsEventException(String message, Throwable cause) {
        super(message, cause);
    }

    public TicketsEventException(Throwable cause) {
        super(cause);
    }
}
