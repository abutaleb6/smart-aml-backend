package com.smartaml.user.api.dto;

import com.smartaml.shared.enums.TenantStatus;
import java.util.UUID;

public record TenantSummary(UUID id, String companyName, String companyEmail, TenantStatus status, int totalScreeningsQuota, int usedScreenings) {}
