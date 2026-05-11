package com.smartaml.screening.api.dto;

import com.smartaml.shared.enums.ScreeningType;
import java.util.UUID;

public record ScreeningRequest(UUID customerId, String name, ScreeningType type, Integer dobYear) {}
