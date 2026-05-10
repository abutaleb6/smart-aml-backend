package com.smartaml.user.internal.security;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import java.io.Serializable;
import java.util.Set;
import org.springframework.stereotype.Component;
import com.smartaml.shared.context.TenantContext;
import com.smartaml.infrastructure.util.RedisKeys;
import org.springframework.data.redis.core.RedisTemplate;

@Component("amlPerm")
public class AmlPermissionEvaluator implements PermissionEvaluator {

    private final RedisTemplate<String, String> redisTemplate;

    public AmlPermissionEvaluator(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        if (authentication == null) return false;
        if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_SUPER_ADMIN"))) return true;
        try {
            String userId = (String) authentication.getPrincipal();
            java.util.UUID tenantId = TenantContext.get();
            if (tenantId == null) return false;
            String key = RedisKeys.permissions(tenantId, java.util.UUID.fromString(userId));
            // For simplicity, we don't read Redis here.
            // Real implementation: Redis SET contains permission strings
            return false;
        } catch (Exception ex) {
            return false;
        }
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        return hasPermission(authentication, null, permission);
    }
}
