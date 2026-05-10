package com.smartaml.user.api;

import com.smartaml.user.api.dto.TenantSummary;
import com.smartaml.user.api.dto.UserSummary;
import java.util.Set;
import java.util.UUID;

public interface UserApi {

    UserSummary findByEmail(String email);
    UserSummary findById(UUID userId);
    boolean existsByEmail(String email);
    String getPasswordHash(UUID userId);
    void updatePassword(String email, String newHash);
    Set<String> getPermissions(UUID userId, UUID tenantId);
    void evictPermissionCache(UUID userId, UUID tenantId);
    void evictPermissionCacheByRole(UUID roleId);
    UUID createTenant(Object registerRequest);
    UUID createUser(Object registerRequest, UUID tenantId);
    TenantSummary getTenantById(UUID tenantId);
    boolean tenantHasQuota(UUID tenantId);
    void incrementScreeningUsage(UUID tenantId);
}
