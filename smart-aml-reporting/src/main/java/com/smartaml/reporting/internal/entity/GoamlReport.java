package com.smartaml.reporting.internal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "goaml_reports")
@Getter
@Setter
@NoArgsConstructor
public class GoamlReport {

    @Id
    @Column(name = "id", columnDefinition = "uuid")
    private UUID id;

    @Column(name = "tenant_id", columnDefinition = "uuid", nullable = false)
    private UUID tenantId;

    @Column(name = "customer_id", columnDefinition = "uuid")
    private UUID customerId;

    @Column(name = "transaction_type")
    private String transactionType;

    @Column(name = "comments")
    private String comments;

    @Column(name = "status_comments")
    private String statusComments;

    @Column(name = "created_by", columnDefinition = "uuid")
    private UUID createdBy;

    @Column(name = "created_at")
    private Instant createdAt = Instant.now();
}
