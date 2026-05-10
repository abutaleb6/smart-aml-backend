package com.smartaml.shared.enums;

/**
 * Customer risk level classification based on risk scoring.
 */
public enum RiskLevel {
    LOW,         // score < 1.5
    LOW_HIGH,    // score < 2.5
    MEDIUM,      // score < 3.5
    HIGH_HIGH,   // score < 4.5
    HIGH         // score >= 4.5
}
