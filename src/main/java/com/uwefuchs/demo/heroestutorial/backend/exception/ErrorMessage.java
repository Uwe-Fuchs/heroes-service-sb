package com.uwefuchs.demo.heroestutorial.backend.exception;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorMessage {
    private final int code;
    private final String message;
    private final String details;

    public ErrorMessage(String message) {
        this(500, message);
    }

    public ErrorMessage(int code, String message) {
        this(code, message, null);
    }

    @JsonCreator
    public ErrorMessage(@JsonProperty("code") int code, @JsonProperty("message") String message,
                        @JsonProperty("details") String details) {
        this.code = code;
        this.message = message;
        this.details = details;
    }

    @JsonProperty("code")
    public Integer getCode() {
        return code;
    }

    @JsonProperty("message")
    public String getMessage() {
        return message;
    }

    @JsonProperty("details")
    public String getDetails() {
        return details;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if ((obj == null) || (getClass() != obj.getClass())) {
            return false;
        }

        final ErrorMessage other = (ErrorMessage) obj;
        return Objects.equals(code, other.code)
                && Objects.equals(message, other.message)
                && Objects.equals(details, other.details);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, message, details);
    }
}