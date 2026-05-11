package com.smartaml.auth.api;

import java.util.List;
import java.util.UUID;

public record AuthenticatedUser(UUID userId, UUID tenantId, String email, List<String> roles, String accessToken, String refreshToken, UUID sessionId) {}
