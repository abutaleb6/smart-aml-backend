package com.smartaml.user.api.dto;

import com.smartaml.shared.enums.UserStatus;
import java.util.List;
import java.util.UUID;

public record UserSummary(UUID id, UUID tenantId, String fullName, String email, UserStatus status, boolean isSuperAdmin, List<String> roleNames) {}
