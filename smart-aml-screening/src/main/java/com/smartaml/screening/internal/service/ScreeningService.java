package com.smartaml.screening.internal.service;

import com.smartaml.screening.api.ScreeningApi;
import com.smartaml.screening.api.dto.ScreeningRequest;
import com.smartaml.screening.api.dto.ScreeningResultSummary;
import com.smartaml.screening.internal.entity.ScreeningCandidate;
import com.smartaml.screening.internal.entity.ScreeningLog;
import com.smartaml.screening.internal.entity.SanctionsEntry;
import com.smartaml.screening.internal.repository.ScreeningCandidateRepository;
import com.smartaml.screening.internal.repository.ScreeningLogRepository;
import com.smartaml.screening.internal.repository.SanctionsEntryRepository;
import com.smartaml.shared.event.ScreeningCompletedEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class ScreeningService implements ScreeningApi {

    private final SanctionsEntryRepository sanctionsEntryRepository;
    private final ScreeningLogRepository screeningLogRepository;
    private final ScreeningCandidateRepository candidateRepository;
    private final ApplicationEventPublisher publisher;

    public ScreeningService(SanctionsEntryRepository sanctionsEntryRepository,
                            ScreeningLogRepository screeningLogRepository,
                            ScreeningCandidateRepository candidateRepository,
                            ApplicationEventPublisher publisher) {
        this.sanctionsEntryRepository = sanctionsEntryRepository;
        this.screeningLogRepository = screeningLogRepository;
        this.candidateRepository = candidateRepository;
        this.publisher = publisher;
    }

    @Override
    public UUID runScreening(ScreeningRequest req) {
        // Very simple screening: finds sanctions entries with name containing search name
        List<SanctionsEntry> matches = sanctionsEntryRepository.findByPrimaryNameContainingIgnoreCase(req.name());
        ScreeningLog log = new ScreeningLog();
        log.setId(UUID.randomUUID());
        log.setTenantId(req.customerId() == null ? null : req.customerId());
        log.setCustomerId(req.customerId());
        log.setSearchName(req.name());
        log.setMatchesFound(matches.size());
        log.setRawResponse("{" + "matches:" + matches.size() + "}");
        screeningLogRepository.save(log);

        for (SanctionsEntry e : matches) {
            ScreeningCandidate c = new ScreeningCandidate();
            c.setId(UUID.randomUUID());
            c.setScreeningLogId(log.getId());
            c.setCandidateName(e.getPrimaryName());
            c.setCandidateData(e.getRawXml());
            candidateRepository.save(c);
        }

        // Publish event for analytics
        publisher.publishEvent(new ScreeningCompletedEvent(this, log.getTenantId(), null, log.getId(), com.smartaml.shared.enums.ScreeningResult.TRUE_MATCH, matches.size()));

        return log.getId();
    }

    @Override
    public ScreeningResultSummary getScreeningLog(UUID logId) {
        return screeningLogRepository.findById(logId)
                .map(l -> new ScreeningResultSummary(l.getId(), l.getTenantId(), l.getCustomerId(), com.smartaml.shared.enums.ScreeningResult.NO_MATCH, l.getMatchesFound()))
                .orElse(null);
    }
}
