package com.smartaml.auth.internal.dto;

import java.util.UUID;

public record LoginResponse(UUID userId, UUID tenantId, String accessToken, String refreshToken, UUID sessionId) {}
