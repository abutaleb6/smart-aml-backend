package com.smartaml.reporting.api;

import com.smartaml.reporting.api.dto.GoAmlReportSummary;
import java.util.List;
import java.util.UUID;

public interface ReportingApi {
    UUID createGoAmlReport(GoAmlReportSummary report);
    GoAmlReportSummary getGoAmlReport(UUID id);
    List<GoAmlReportSummary> listByTenant(UUID tenantId, int page, int size);
}
