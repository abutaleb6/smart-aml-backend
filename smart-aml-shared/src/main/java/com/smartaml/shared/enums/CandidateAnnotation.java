package com.smartaml.shared.enums;

/**
 * Annotation status for screening candidates (manual review outcome).
 */
public enum CandidateAnnotation {
    RELEVANT,     // Human reviewer confirmed match
    IRRELEVANT    // Human reviewer dismissed as false positive
}
