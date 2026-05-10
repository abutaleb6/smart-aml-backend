package com.smartaml.infrastructure.util;

import java.util.UUID;

public final class RedisKeys {

    public static String session(UUID userId) { return "session:" + userId; }
    public static String permissions(UUID tid, UUID uid) { return "perms:" + tid + ":" + uid; }
    public static String blacklist(String jti) { return "blacklist:" + jti; }
    public static String rateLimit(String ip, String email) { return "rate:" + ip + ":" + email; }
    public static String analytics(UUID tenantId) { return "analytics:" + tenantId; }
    public static String screeningCache(String hash) { return "screening:" + hash; }
    public static String quota(UUID tenantId) { return "quota:" + tenantId; }
    public static String batchJob(String jobId) { return "batch:" + jobId; }
}
