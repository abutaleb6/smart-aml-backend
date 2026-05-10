package com.smartaml.shared.context;

import java.util.UUID;

/**
 * ThreadLocal context for tenant isolation.
 * Uses InheritableThreadLocal so child threads inherit parent context.
 */
public class TenantContext {

    private static final InheritableThreadLocal<UUID> TENANT_ID = new InheritableThreadLocal<>();
    private static final InheritableThreadLocal<Boolean> IS_SUPER_ADMIN = new InheritableThreadLocal<>();

    /**
     * Set the current tenant ID.
     */
    public static void set(UUID tenantId) {
        TENANT_ID.set(tenantId);
    }

    /**
     * Get the current tenant ID.
     *
     * @return tenant ID or null if not set
     */
    public static UUID get() {
        return TENANT_ID.get();
    }

    /**
     * Check if current user is super admin.
     */
    public static boolean isSuperAdmin() {
        Boolean isSuperAdmin = IS_SUPER_ADMIN.get();
        return isSuperAdmin != null && isSuperAdmin;
    }

    /**
     * Set super admin flag.
     */
    public static void setSuperAdmin(boolean value) {
        IS_SUPER_ADMIN.set(value);
    }

    /**
     * Clear the context (cleanup).
     */
    public static void clear() {
        TENANT_ID.remove();
        IS_SUPER_ADMIN.remove();
    }
}
