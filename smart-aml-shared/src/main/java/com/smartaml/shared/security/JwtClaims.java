package com.smartaml.shared.security;

import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * JWT token claims extracted during validation.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtClaims {
    private UUID userId;          // Subject (sub claim)
    private UUID tenantId;        // Tenant context
    private String email;         // User email
    private List<String> roles;   // Role list (authorities)
    private boolean isSuperAdmin; // Super admin flag
    private String jti;           // JWT ID (for blacklisting)
}
