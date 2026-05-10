package com.smartaml.user.internal.service;

import com.smartaml.user.api.UserApi;
import com.smartaml.user.api.dto.TenantSummary;
import com.smartaml.user.api.dto.UserSummary;
import com.smartaml.user.internal.entity.User;
import com.smartaml.user.internal.repository.UserRepository;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserApiImpl implements UserApi {

    private final UserRepository userRepository;

    public UserApiImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserSummary findByEmail(String email) {
        Optional<User> u = userRepository.findByEmail(email);
        if (u.isEmpty()) return null;
        User user = u.get();
        return new UserSummary(user.getId(), user.getTenantId(), user.getFullName(), user.getEmail(), user.getStatus(), user.isSuperAdmin(), java.util.List.of());
    }

    @Override
    public UserSummary findById(UUID userId) {
        return userRepository.findById(userId).map(u -> new UserSummary(u.getId(), u.getTenantId(), u.getFullName(), u.getEmail(), u.getStatus(), u.isSuperAdmin(), java.util.List.of())).orElse(null);
    }

    @Override
    public boolean existsByEmail(String email) { return userRepository.existsByEmail(email); }

    @Override
    public String getPasswordHash(UUID userId) {
        return userRepository.findById(userId).map(User::getPasswordHash).orElse(null);
    }

    @Override
    public void updatePassword(String email, String newHash) {
        userRepository.findByEmail(email).ifPresent(u -> { u.setPasswordHash(newHash); userRepository.save(u); });
    }

    @Override
    public Set<String> getPermissions(UUID userId, UUID tenantId) {
        // Simplified: load from DB or return empty set; real implementation joins user_roles->role_permissions
        return Set.of();
    }

    @Override
    public void evictPermissionCache(UUID userId, UUID tenantId) {
        // noop for now
    }

    @Override
    public void evictPermissionCacheByRole(UUID roleId) {}

    @Override
    public UUID createTenant(Object registerRequest) { return UUID.randomUUID(); }

    @Override
    public UUID createUser(Object registerRequest, UUID tenantId) { return UUID.randomUUID(); }

    @Override
    public TenantSummary getTenantById(UUID tenantId) { return null; }

    @Override
    public boolean tenantHasQuota(UUID tenantId) { return true; }

    @Override
    public void incrementScreeningUsage(UUID tenantId) {}
}
