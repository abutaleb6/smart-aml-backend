package com.smartaml.shared.event;

import java.time.Instant;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.ApplicationEvent;

/**
 * Base class for all cross-module domain events.
 * Uses Spring ApplicationEvent for in-process event bus.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public abstract class BaseModuleEvent extends ApplicationEvent {

    private UUID tenantId;           // Tenant context
    private UUID triggeredByUserId;  // User who triggered the event
    private Instant occurredAt;      // When the event occurred

    /**
     * Constructor required by Spring ApplicationEvent.
     */
    public BaseModuleEvent(Object source, UUID tenantId, UUID triggeredByUserId) {
        super(source);
        this.tenantId = tenantId;
        this.triggeredByUserId = triggeredByUserId;
        this.occurredAt = Instant.now();
    }
}
