package com.smartaml.screening.internal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.UUID;
import jakarta.persistence.Lob;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "screening_logs")
@Getter
@Setter
@NoArgsConstructor
public class ScreeningLog {

    @Id
    @Column(name = "id", columnDefinition = "uuid")
    private UUID id;

    @Column(name = "tenant_id", columnDefinition = "uuid", nullable = false)
    private UUID tenantId;

    @Column(name = "user_id", columnDefinition = "uuid")
    private UUID userId;

    @Column(name = "customer_id", columnDefinition = "uuid")
    private UUID customerId;

    @Column(name = "search_name")
    private String searchName;

    @Column(name = "matches_found")
    private int matchesFound;

    @Lob
    @Column(name = "raw_response")
    private String rawResponse;

    @Column(name = "created_at")
    private Instant createdAt = Instant.now();
}
