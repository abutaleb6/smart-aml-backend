package com.smartaml.shared.event;

import com.smartaml.shared.enums.CustomerType;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Published when a customer submits their KYC application.
 * Consumed by: audit, screening, email modules.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class CustomerSubmittedEvent extends BaseModuleEvent {

    private UUID customerId;
    private CustomerType customerType;

    public CustomerSubmittedEvent(Object source, UUID tenantId, UUID triggeredByUserId,
                                  UUID customerId, CustomerType customerType) {
        super(source, tenantId, triggeredByUserId);
        this.customerId = customerId;
        this.customerType = customerType;
    }
}
