package com.smartaml.shared.exception;

/**
 * Exception thrown when a file cannot be found.
 */
public class FileNotFoundException extends RuntimeException {

    public FileNotFoundException(String message) {
        super(message);
    }
}
