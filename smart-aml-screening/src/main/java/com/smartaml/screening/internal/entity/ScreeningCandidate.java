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
@Table(name = "screening_candidates")
@Getter
@Setter
@NoArgsConstructor
public class ScreeningCandidate {

    @Id
    @Column(name = "id", columnDefinition = "uuid")
    private UUID id;

    @Column(name = "screening_log_id", columnDefinition = "uuid", nullable = false)
    private UUID screeningLogId;

    @Column(name = "candidate_name")
    private String candidateName;

    @Lob
    @Column(name = "candidate_data")
    private String candidateData;

    @Column(name = "annotation")
    private String annotation;

    @Column(name = "created_at")
    private Instant createdAt = Instant.now();
}
