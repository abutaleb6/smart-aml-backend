package com.smartaml.shared.enums;

/**
 * Type of screening to perform against sanctions list.
 */
public enum ScreeningType {
    INDIVIDUAL,  // Screening against individual entries
    CORPORATE,   // Screening against corporate entities
    VESSEL       // Screening against vessel entries
}
