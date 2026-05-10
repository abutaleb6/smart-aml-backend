package com.smartaml.shared.exception;

/**
 * Exception thrown when a tenant quota is exceeded.
 */
public class QuotaExceededException extends RuntimeException {

    public QuotaExceededException(String message) {
        super(message);
    }
}
