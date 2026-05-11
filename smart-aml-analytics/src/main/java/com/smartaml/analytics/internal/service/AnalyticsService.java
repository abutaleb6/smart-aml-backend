package com.smartaml.analytics.internal.service;

import com.smartaml.analytics.api.AnalyticsApi;
import com.smartaml.analytics.api.dto.AnalyticsSummary;
import com.smartaml.analytics.api.dto.ScreeningMetrics;
import com.smartaml.analytics.internal.entity.AnalyticsMetric;
import com.smartaml.analytics.internal.repository.AnalyticsMetricRepository;
import com.smartaml.shared.event.ScreeningCompletedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.UUID;

@Service
@Transactional
public class AnalyticsService implements AnalyticsApi {

    private final AnalyticsMetricRepository repo;

    public AnalyticsService(AnalyticsMetricRepository repo) {
        this.repo = repo;
    }

    @Override
    public AnalyticsSummary getTenantSummary(UUID tenantId, LocalDate from, LocalDate to) {
        List<AnalyticsMetric> metrics = repo.findByTenantIdAndMetricDateBetween(tenantId, from, to);
        long screenings = metrics.stream().mapToLong(AnalyticsMetric::getScreeningsRun).sum();
        long matches = metrics.stream().mapToLong(AnalyticsMetric::getMatchesFound).sum();
        // totalTenants not accurate per-tenant but provided as placeholder
        return new AnalyticsSummary(1, screenings, matches);
    }

    @Override
    public ScreeningMetrics getScreeningMetrics(UUID tenantId, LocalDate date) {
        AnalyticsMetric m = repo.findByTenantIdAndMetricDate(tenantId, date);
        if (m == null) return new ScreeningMetrics(date, 0, 0);
        return new ScreeningMetrics(date, m.getScreeningsRun(), m.getMatchesFound());
    }

    @EventListener
    public void onScreeningCompleted(ScreeningCompletedEvent ev) {
        // Aggregate into analytics_metrics by date
        UUID tenantId = ev.tenantId();
        LocalDate date = LocalDate.now();
        AnalyticsMetric m = repo.findByTenantIdAndMetricDate(tenantId, date);
        if (m == null) {
            m = new AnalyticsMetric();
            m.setId(UUID.randomUUID());
            m.setTenantId(tenantId);
            m.setMetricDate(date);
            m.setScreeningsRun(ev.matchesFound() >= 0 ? 1 : 1);
            m.setMatchesFound(ev.matchesFound());
        } else {
            m.setScreeningsRun(m.getScreeningsRun() + 1);
            m.setMatchesFound(m.getMatchesFound() + ev.matchesFound());
        }
        repo.save(m);
    }
}
