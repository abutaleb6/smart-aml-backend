package com.smartaml.shared.event;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Published when a customer is approved by compliance.
 * Consumed by: analytics, email modules.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class CustomerApprovedEvent extends BaseModuleEvent {

    private UUID customerId;

    public CustomerApprovedEvent(Object source, UUID tenantId, UUID triggeredByUserId,
                                 UUID customerId) {
        super(source, tenantId, triggeredByUserId);
        this.customerId = customerId;
    }
}
