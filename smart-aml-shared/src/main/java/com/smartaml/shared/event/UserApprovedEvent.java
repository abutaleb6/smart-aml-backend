package com.smartaml.shared.event;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Published when a user is approved (after tenant approval).
 * Consumed by: email module.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class UserApprovedEvent extends BaseModuleEvent {

    private UUID userId;
    private String email;
    private String fullName;
    private String companyName;

    public UserApprovedEvent(Object source, UUID tenantId, UUID triggeredByUserId,
                             UUID userId, String email, String fullName, String companyName) {
        super(source, tenantId, triggeredByUserId);
        this.userId = userId;
        this.email = email;
        this.fullName = fullName;
        this.companyName = companyName;
    }
}
