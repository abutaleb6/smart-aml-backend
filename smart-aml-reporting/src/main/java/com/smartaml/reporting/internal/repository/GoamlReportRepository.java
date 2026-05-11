package com.smartaml.reporting.internal.repository;

import com.smartaml.reporting.internal.entity.GoamlReport;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoamlReportRepository extends JpaRepository<GoamlReport, UUID> {
    List<GoamlReport> findByTenantId(UUID tenantId, Pageable pageable);
}
