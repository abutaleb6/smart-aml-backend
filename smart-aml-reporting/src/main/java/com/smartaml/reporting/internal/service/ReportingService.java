package com.smartaml.reporting.internal.service;

import com.smartaml.reporting.api.ReportingApi;
import com.smartaml.reporting.api.dto.GoAmlReportSummary;
import com.smartaml.reporting.internal.entity.GoamlReport;
import com.smartaml.reporting.internal.repository.GoamlReportRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class ReportingService implements ReportingApi {

    private final GoamlReportRepository repo;

    public ReportingService(GoamlReportRepository repo) {
        this.repo = repo;
    }

    @Override
    public UUID createGoAmlReport(GoAmlReportSummary report) {
        GoamlReport r = new GoamlReport();
        r.setId(report.id() == null ? UUID.randomUUID() : report.id());
        r.setTenantId(report.tenantId());
        r.setCustomerId(report.customerId());
        r.setTransactionType(report.transactionType());
        r.setComments(report.comments());
        r.setStatusComments(report.status());
        r.setCreatedAt(report.createdAt() == null ? java.time.Instant.now() : report.createdAt());
        repo.save(r);
        return r.getId();
    }

    @Override
    public GoAmlReportSummary getGoAmlReport(UUID id) {
        return repo.findById(id).map(r -> new GoAmlReportSummary(r.getId(), r.getTenantId(), r.getCustomerId(), r.getTransactionType(), r.getComments(), r.getStatusComments(), r.getCreatedAt())).orElse(null);
    }

    @Override
    public List<GoAmlReportSummary> listByTenant(UUID tenantId, int page, int size) {
        var list = repo.findByTenantId(tenantId, PageRequest.of(page, size));
        return list.stream().map(r -> new GoAmlReportSummary(r.getId(), r.getTenantId(), r.getCustomerId(), r.getTransactionType(), r.getComments(), r.getStatusComments(), r.getCreatedAt())).collect(Collectors.toList());
    }
}
