package com.smartaml.infrastructure.security;

import com.smartaml.shared.security.JwtClaims;
import com.smartaml.shared.exception.UnauthorizedException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.access-token-expiry:900000}")
    private long accessExpiryMs;

    @Value("${jwt.refresh-token-expiry:604800000}")
    private long refreshExpiryMs;

    private Key signingKey;

    @PostConstruct
    public void init() {
        signingKey = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generateAccessToken(UUID userId, UUID tenantId, String email, List<String> roles, boolean isSuperAdmin) {
        long now = System.currentTimeMillis();
        JwtBuilder b = Jwts.builder()
                .setSubject(userId.toString())
                .claim("tenantId", tenantId != null ? tenantId.toString() : null)
                .claim("email", email)
                .claim("roles", roles)
                .claim("isSuperAdmin", isSuperAdmin)
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + accessExpiryMs))
                .signWith(signingKey, SignatureAlgorithm.HS512);
        return b.compact();
    }

    public String generateRefreshToken(UUID userId, UUID tenantId) {
        long now = System.currentTimeMillis();
        return Jwts.builder()
                .setSubject(userId.toString())
                .claim("tenantId", tenantId != null ? tenantId.toString() : null)
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + refreshExpiryMs))
                .signWith(signingKey, SignatureAlgorithm.HS512)
                .compact();
    }

    public JwtClaims validateAndExtract(String token) {
        try {
            Jws<Claims> parsed = Jwts.parserBuilder().setSigningKey(signingKey).build().parseClaimsJws(token);
            Claims c = parsed.getBody();
            UUID userId = UUID.fromString(c.getSubject());
            UUID tenantId = null;
            if (c.get("tenantId") != null) tenantId = UUID.fromString(c.get("tenantId").toString());
            String email = c.get("email", String.class);
            @SuppressWarnings("unchecked")
            List<String> roles = (List<String>) c.get("roles");
            boolean isSuperAdmin = c.get("isSuperAdmin", Boolean.class) != null ? c.get("isSuperAdmin", Boolean.class) : false;
            String jti = c.getId();
            return JwtClaims.builder()
                    .userId(userId)
                    .tenantId(tenantId)
                    .email(email)
                    .roles(roles)
                    .isSuperAdmin(isSuperAdmin)
                    .jti(jti)
                    .build();
        } catch (JwtException | IllegalArgumentException e) {
            throw new UnauthorizedException("Invalid JWT token");
        }
    }
}
