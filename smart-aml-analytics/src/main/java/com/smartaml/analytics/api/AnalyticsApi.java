package com.smartaml.analytics.api;

import com.smartaml.analytics.api.dto.AnalyticsSummary;
import com.smartaml.analytics.api.dto.ScreeningMetrics;
import java.time.LocalDate;
import java.util.UUID;

public interface AnalyticsApi {
    AnalyticsSummary getTenantSummary(UUID tenantId, LocalDate from, LocalDate to);
    ScreeningMetrics getScreeningMetrics(UUID tenantId, LocalDate date);
}
