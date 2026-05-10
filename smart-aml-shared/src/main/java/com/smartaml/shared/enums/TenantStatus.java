package com.smartaml.shared.enums;

/**
 * Tenant (company) status lifecycle.
 */
public enum TenantStatus {
    PENDING_APPROVAL,  // Awaiting admin approval
    ACTIVE,            // Approved and active
    SUSPENDED          // Temporarily suspended
}
