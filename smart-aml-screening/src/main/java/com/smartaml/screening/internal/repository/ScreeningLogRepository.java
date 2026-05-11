package com.smartaml.screening.internal.repository;

import com.smartaml.screening.internal.entity.ScreeningLog;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScreeningLogRepository extends JpaRepository<ScreeningLog, UUID> {
    List<ScreeningLog> findByTenantId(UUID tenantId);
}
