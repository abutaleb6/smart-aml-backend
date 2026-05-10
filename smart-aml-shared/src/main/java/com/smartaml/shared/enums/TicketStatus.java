package com.smartaml.shared.enums;

/**
 * Support ticket status lifecycle.
 */
public enum TicketStatus {
    OPEN,        // Newly created
    IN_PROGRESS, // Being worked on
    RESOLVED,    // Resolved but not closed
    CLOSED       // Closed by user or admin
}
