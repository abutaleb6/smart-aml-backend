package com.smartaml.screening.api.dto;

import com.smartaml.shared.enums.ScreeningResult;
import java.util.UUID;

public record ScreeningResultSummary(UUID id, UUID tenantId, UUID customerId, ScreeningResult result, int matchesFound) {}
