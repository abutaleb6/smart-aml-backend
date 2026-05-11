package com.smartaml.screening.internal.repository;

import com.smartaml.screening.internal.entity.ScreeningCandidate;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScreeningCandidateRepository extends JpaRepository<ScreeningCandidate, UUID> {
    List<ScreeningCandidate> findByScreeningLogId(UUID logId);
}
