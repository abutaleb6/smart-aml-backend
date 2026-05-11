package com.smartaml.auth.internal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_sessions")
@Getter
@Setter
@NoArgsConstructor
public class UserSession {

    @Id
    @GeneratedValue
    @Column(name = "id", columnDefinition = "uuid")
    private UUID id;

    @Column(name = "user_id", columnDefinition = "uuid", nullable = false)
    private UUID userId;

    @Column(name = "refresh_token_hash", nullable = false)
    private String refreshTokenHash;

    @Column(name = "ip_address")
    private String ipAddress;

    @Column(name = "device_type")
    private String deviceType;

    @Column(name = "os")
    private String os;

    @Column(name = "browser_name")
    private String browserName;

    @Column(name = "browser_version")
    private String browserVersion;

    @Column(name = "user_agent", columnDefinition = "text")
    private String userAgent;

    @Column(name = "is_mobile_app")
    private boolean isMobileApp;

    @Column(name = "app_source")
    private String appSource;

    @Column(name = "device_id")
    private String deviceId;

    @Column(name = "is_active")
    private boolean isActive = true;

    @Column(name = "created_at")
    private Instant createdAt = Instant.now();

    @Column(name = "expires_at")
    private Instant expiresAt;
}
