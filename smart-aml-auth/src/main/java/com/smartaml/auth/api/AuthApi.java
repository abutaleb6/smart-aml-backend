package com.smartaml.auth.api;

import com.smartaml.auth.api.AuthenticatedUser;
import java.util.UUID;

public interface AuthApi {
    AuthenticatedUser login(String email, String password);
    String refresh(String refreshToken);
    void logout(UUID sessionId, String jti);
}
