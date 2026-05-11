package com.smartaml.auth.internal.dto;

public record RegisterRequest(String fullName, String email, String password, String companyName) {}
