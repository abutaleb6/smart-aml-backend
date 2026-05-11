package com.smartaml.reporting.web;

import com.smartaml.reporting.api.ReportingApi;
import com.smartaml.reporting.api.dto.GoAmlReportSummary;
import com.smartaml.shared.response.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/reporting")
public class ReportingController {

    private final ReportingApi reportingApi;

    public ReportingController(ReportingApi reportingApi) { this.reportingApi = reportingApi; }

    @PostMapping("/goaml")
    public ApiResponse<UUID> create(@RequestBody GoAmlReportSummary req) {
        UUID id = reportingApi.createGoAmlReport(req);
        return ApiResponse.created(id);
    }

    @GetMapping("/goaml/{id}")
    public ApiResponse<GoAmlReportSummary> get(@PathVariable UUID id) {
        return ApiResponse.ok(reportingApi.getGoAmlReport(id));
    }

    @GetMapping("/goaml")
    public ApiResponse<List<GoAmlReportSummary>> list(@RequestParam UUID tenantId, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size) {
        return ApiResponse.ok(reportingApi.listByTenant(tenantId, page, size));
    }
}
