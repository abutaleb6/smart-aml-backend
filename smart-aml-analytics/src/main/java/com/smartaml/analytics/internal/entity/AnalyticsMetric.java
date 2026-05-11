package com.smartaml.analytics.internal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "analytics_metrics")
@Getter
@Setter
@NoArgsConstructor
public class AnalyticsMetric {

    @Id
    @Column(name = "id", columnDefinition = "uuid")
    private UUID id;

    @Column(name = "tenant_id", columnDefinition = "uuid", nullable = false)
    private UUID tenantId;

    @Column(name = "metric_date")
    private LocalDate metricDate;

    @Column(name = "screenings_run")
    private long screeningsRun;

    @Column(name = "matches_found")
    private long matchesFound;
}
