package com.smartaml.customer.api.dto;

import com.smartaml.shared.enums.CustomerStatus;
import com.smartaml.shared.enums.CustomerType;
import java.util.UUID;

public record CustomerSummary(UUID id, UUID tenantId, CustomerType customerType, CustomerStatus status, double riskScore) {}
