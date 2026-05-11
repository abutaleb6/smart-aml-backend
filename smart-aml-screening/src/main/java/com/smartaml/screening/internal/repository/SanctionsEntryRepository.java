package com.smartaml.screening.internal.repository;

import com.smartaml.screening.internal.entity.SanctionsEntry;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SanctionsEntryRepository extends JpaRepository<SanctionsEntry, UUID> {
    List<SanctionsEntry> findByPrimaryNameContainingIgnoreCase(String q);
}
