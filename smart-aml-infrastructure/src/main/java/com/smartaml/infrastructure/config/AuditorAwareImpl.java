package com.smartaml.infrastructure.config;

import java.util.Optional;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;
import java.util.UUID;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;

@Component
public class AuditorAwareImpl implements AuditorAware<UUID> {

    @Override
    public Optional<UUID> getCurrentAuditor() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            return Optional.empty();
        }
        Object principal = auth.getPrincipal();
        if (principal instanceof org.springframework.security.core.userdetails.User) {
            // If using custom principal, adapt accordingly
            return Optional.empty();
        }
        // Expect a UUID string as principal
        try {
            String subj = auth.getName();
            return Optional.of(UUID.fromString(subj));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
