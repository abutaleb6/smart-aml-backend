package com.smartaml.analytics.internal.repository;

import com.smartaml.analytics.internal.entity.AnalyticsMetric;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnalyticsMetricRepository extends JpaRepository<AnalyticsMetric, UUID> {
    List<AnalyticsMetric> findByTenantIdAndMetricDateBetween(UUID tenantId, LocalDate from, LocalDate to);
    AnalyticsMetric findByTenantIdAndMetricDate(UUID tenantId, LocalDate date);
}
