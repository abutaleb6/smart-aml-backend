package com.smartaml.shared.event;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Published when a tenant (company) is approved by super admin.
 * Consumed by: email module.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class TenantApprovedEvent extends BaseModuleEvent {

    private UUID tenantId;
    private String companyName;
    private String contactEmail;

    public TenantApprovedEvent(Object source, UUID triggeredByAdminUserId,
                               UUID tenantId, String companyName, String contactEmail) {
        super(source, tenantId, triggeredByAdminUserId);
        this.tenantId = tenantId;
        this.companyName = companyName;
        this.contactEmail = contactEmail;
    }
}
