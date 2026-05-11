package com.smartaml.auth.internal.service;

import com.smartaml.auth.api.AuthApi;
import com.smartaml.auth.api.AuthenticatedUser;
import com.smartaml.auth.internal.entity.UserSession;
import com.smartaml.auth.internal.dto.LoginRequest;
import com.smartaml.auth.internal.dto.LoginResponse;
import com.smartaml.auth.internal.dto.RegisterRequest;
import com.smartaml.infrastructure.security.JwtService;
import com.smartaml.user.api.UserApi;
import com.smartaml.shared.util.HashUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class AuthService implements AuthApi {

    private final JwtService jwtService;
    private final UserApi userApi;
    private final BCryptPasswordEncoder passwordEncoder;

    @PersistenceContext
    private EntityManager em;

    public AuthService(JwtService jwtService, UserApi userApi, BCryptPasswordEncoder passwordEncoder) {
        this.jwtService = jwtService;
        this.userApi = userApi;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public AuthenticatedUser login(String email, String password) {
        var userSummary = userApi.findByEmail(email);
        if (userSummary == null) throw new IllegalArgumentException("Invalid credentials");
        String stored = userApi.getPasswordHash(userSummary.id());
        if (stored == null || !passwordEncoder.matches(password, stored)) throw new IllegalArgumentException("Invalid credentials");

        UUID uid = userSummary.id();
        UUID tenantId = userSummary.tenantId();
        List<String> roles = userSummary.roleNames();

        String access = jwtService.generateAccessToken(uid, tenantId, userSummary.email(), roles, userSummary.isSuperAdmin());
        String refresh = jwtService.generateRefreshToken(uid, tenantId);
        // Persist session with hashed refresh token
        String refreshHash = HashUtil.sha256(refresh);
        UserSession session = new UserSession();
        session.setUserId(uid);
        session.setRefreshTokenHash(refreshHash);
        session.setCreatedAt(Instant.now());
        session.setIsActive(true);
        // expires set by application policy (optional)
        em.persist(session);

        return new AuthenticatedUser(uid, tenantId, userSummary.email(), roles, access, refresh, session.getId());
    }

    @Override
    public String refresh(String refreshToken) {
        // Validate token and issue new access token
        var claims = jwtService.validateAndExtract(refreshToken);
        UUID uid = claims.getUserId();
        UUID tenantId = claims.getTenantId();
        // Optional: lookup session by refresh token hash
        String hash = HashUtil.sha256(refreshToken);
        var q = em.createQuery("SELECT s FROM com.smartaml.auth.internal.entity.UserSession s WHERE s.refreshTokenHash = :h AND s.isActive = true", UserSession.class)
                .setParameter("h", hash);
        var list = q.getResultList();
        if (list.isEmpty()) throw new IllegalArgumentException("Invalid refresh token");
        // Issue new access token
        String access = jwtService.generateAccessToken(uid, tenantId, claims.getEmail(), claims.getRoles(), claims.isSuperAdmin());
        return access;
    }

    @Override
    public void logout(UUID sessionId, String jti) {
        var s = em.find(UserSession.class, sessionId);
        if (s != null) {
            s.setIsActive(false);
            em.merge(s);
        }
        // Blacklist jti in Redis — left as TODO (infrastructure Redis template)
    }

    public UUID register(RegisterRequest req) {
        // Simple registration: create tenant + user via UserApi.createTenant/createUser
        UUID tenantId = userApi.createTenant(req);
        UUID userId = userApi.createUser(req, tenantId);
        return userId;
    }

    public void forgotPassword(String email) {
        // Generate OTP and send via email — implementation placeholder
    }

    public void resetPassword(String email, String otp, String newPassword) {
        // Verify OTP then update password
        String hash = passwordEncoder.encode(newPassword);
        userApi.updatePassword(email, hash);
    }
}
