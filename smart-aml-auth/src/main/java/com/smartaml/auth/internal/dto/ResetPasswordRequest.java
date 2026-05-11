package com.smartaml.auth.internal.dto;

public record ResetPasswordRequest(String email, String otp, String newPassword) {}
