package com.smartaml.auth.web;

import com.smartaml.auth.api.AuthApi;
import com.smartaml.auth.api.AuthenticatedUser;
import com.smartaml.auth.internal.dto.LoginRequest;
import com.smartaml.auth.internal.dto.LoginResponse;
import com.smartaml.auth.internal.dto.RegisterRequest;
import com.smartaml.shared.response.ApiResponse;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthApi authApi;
    private final com.smartaml.auth.internal.service.AuthService authService;

    public AuthController(AuthApi authApi, com.smartaml.auth.internal.service.AuthService authService) {
        this.authApi = authApi;
        this.authService = authService;
    }

    @PostMapping("/login")
    public ApiResponse<?> login(@RequestBody LoginRequest req) {
        AuthenticatedUser u = authApi.login(req.email(), req.password());
        return ApiResponse.ok(new LoginResponse(u.userId(), u.tenantId(), u.accessToken(), u.refreshToken(), u.sessionId()));
    }

    @PostMapping("/refresh")
    public ApiResponse<?> refresh(@RequestBody String refreshToken) {
        String access = authApi.refresh(refreshToken);
        return ApiResponse.ok(access);
    }

    @PostMapping("/logout")
    public ApiResponse<?> logout(@RequestParam UUID sessionId, @RequestParam String jti) {
        authApi.logout(sessionId, jti);
        return ApiResponse.message("Logged out");
    }

    @PostMapping("/register")
    public ApiResponse<?> register(@RequestBody RegisterRequest req) {
        UUID userId = authService.register(req);
        return ApiResponse.created(userId);
    }
}
