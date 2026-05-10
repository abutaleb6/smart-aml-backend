package com.smartaml.shared.enums;

/**
 * All possible audit trail actions across the platform.
 */
public enum AuditAction {
    // Authentication
    LOGIN,
    LOGOUT,
    LOGIN_FAILED,
    REGISTER,

    // Password Management
    PASSWORD_RESET_REQUESTED,
    PASSWORD_RESET_COMPLETED,
    PASSWORD_CHANGED,

    // User Management
    USER_CREATED,
    USER_UPDATED,
    USER_ACTIVATED,
    USER_SUSPENDED,
    USER_DELETED,

    // Role Management
    ROLE_ASSIGNED,
    ROLE_REMOVED,
    ROLE_CREATED,
    ROLE_UPDATED,
    ROLE_DELETED,

    // Permission Management
    PERMISSION_CHANGED,

    // Tenant Management
    TENANT_REGISTERED,
    TENANT_APPROVED,
    TENANT_REJECTED,
    TENANT_SUSPENDED,

    // Quota Management
    QUOTA_UPDATED,

    // Customer Management
    CUSTOMER_CREATED,
    CUSTOMER_UPDATED,
    CUSTOMER_SUBMITTED,
    CUSTOMER_APPROVED,
    CUSTOMER_REJECTED,

    // Document Management
    DOCUMENT_UPLOADED,

    // Screening
    SCREENING_RUN,
    BATCH_SCREENING_RUN,
    CANDIDATE_ANNOTATED,

    // Reporting
    REPORT_CREATED,
    REPORT_UPDATED,
    REPORT_DELETED,
    REPORT_EXPORTED
}
