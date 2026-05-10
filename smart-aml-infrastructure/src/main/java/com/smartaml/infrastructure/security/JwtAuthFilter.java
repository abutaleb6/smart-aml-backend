package com.smartaml.infrastructure.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartaml.shared.response.ApiResponse;
import com.smartaml.shared.security.JwtClaims;
import com.smartaml.shared.exception.UnauthorizedException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final ObjectMapper objectMapper;

    public JwtAuthFilter(JwtService jwtService, ObjectMapper objectMapper) {
        this.jwtService = jwtService;
        this.objectMapper = objectMapper;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return path.startsWith("/api/v1/auth") || path.startsWith("/swagger-ui") || path.startsWith("/v3/api-docs") || path.startsWith("/actuator/health");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        if (!StringUtils.hasText(header) || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = header.substring(7);
        try {
            JwtClaims claims = jwtService.validateAndExtract(token);
            // TODO: check blacklist via Redis (infrastructure Redis service)
            List<String> roles = claims.getRoles();
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                    claims.getUserId().toString(), null,
                    roles == null ? List.of() : roles.stream().map(r -> new SimpleGrantedAuthority("ROLE_" + r)).toList()
            );
            SecurityContextHolder.getContext().setAuthentication(auth);
            // set request attributes
            request.setAttribute("userId", claims.getUserId());
            request.setAttribute("tenantId", claims.getTenantId());
            request.setAttribute("isSuperAdmin", claims.isSuperAdmin());
            request.setAttribute("jti", claims.getJti());
            filterChain.doFilter(request, response);
        } catch (UnauthorizedException ex) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            objectMapper.writeValue(response.getWriter(), ApiResponse.error(ex.getMessage()));
        }
    }
}
