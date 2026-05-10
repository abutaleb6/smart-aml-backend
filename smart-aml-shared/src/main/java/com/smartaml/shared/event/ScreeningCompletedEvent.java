package com.smartaml.shared.event;

import com.smartaml.shared.enums.ScreeningResult;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Published when screening completes against sanctions list.
 * Consumed by: analytics module.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class ScreeningCompletedEvent extends BaseModuleEvent {

    private UUID screeningLogId;
    private ScreeningResult result;
    private int matchesFound;

    public ScreeningCompletedEvent(Object source, UUID tenantId, UUID triggeredByUserId,
                                   UUID screeningLogId, ScreeningResult result, int matchesFound) {
        super(source, tenantId, triggeredByUserId);
        this.screeningLogId = screeningLogId;
        this.result = result;
        this.matchesFound = matchesFound;
    }
}
