package com.smartaml.shared.enums;

/**
 * Customer onboarding status lifecycle.
 */
public enum CustomerStatus {
    DRAFT,       // Initial state, not submitted
    SUBMITTED,   // Submitted for review
    IN_REVIEW,   // Under review by compliance
    APPROVED,    // KYC approved
    REJECTED     // KYC rejected
}
