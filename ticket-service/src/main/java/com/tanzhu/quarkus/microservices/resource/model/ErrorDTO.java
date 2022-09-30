package com.tanzhu.quarkus.microservices.resource.model;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class ErrorDTO {
    private String message;
    private String severity;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }
}
