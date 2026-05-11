package com.smartaml.analytics.web;

import com.smartaml.analytics.api.AnalyticsApi;
import com.smartaml.analytics.api.dto.AnalyticsSummary;
import com.smartaml.analytics.api.dto.ScreeningMetrics;
import com.smartaml.shared.response.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/analytics")
public class AnalyticsController {

    private final AnalyticsApi analyticsApi;

    public AnalyticsController(AnalyticsApi analyticsApi) { this.analyticsApi = analyticsApi; }

    @GetMapping("/tenant/{tenantId}/summary")
    public ApiResponse<AnalyticsSummary> tenantSummary(@PathVariable UUID tenantId,
                                                       @RequestParam(required = false) String from,
                                                       @RequestParam(required = false) String to) {
        LocalDate f = from == null ? LocalDate.now().minusDays(7) : LocalDate.parse(from);
        LocalDate t = to == null ? LocalDate.now() : LocalDate.parse(to);
        return ApiResponse.ok(analyticsApi.getTenantSummary(tenantId, f, t));
    }

    @GetMapping("/tenant/{tenantId}/screening/{date}")
    public ApiResponse<ScreeningMetrics> screeningMetrics(@PathVariable UUID tenantId, @PathVariable String date) {
        LocalDate d = LocalDate.parse(date);
        return ApiResponse.ok(analyticsApi.getScreeningMetrics(tenantId, d));
    }
}
