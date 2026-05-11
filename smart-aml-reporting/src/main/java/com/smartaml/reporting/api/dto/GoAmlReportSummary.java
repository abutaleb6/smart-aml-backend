package com.smartaml.reporting.api.dto;

import java.util.UUID;
import java.time.Instant;

public record GoAmlReportSummary(UUID id, UUID tenantId, UUID customerId, String transactionType, String comments, String status, Instant createdAt) {}
