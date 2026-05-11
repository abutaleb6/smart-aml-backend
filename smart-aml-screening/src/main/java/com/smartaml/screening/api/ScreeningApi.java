package com.smartaml.screening.api;

import com.smartaml.screening.api.dto.ScreeningRequest;
import com.smartaml.screening.api.dto.ScreeningResultSummary;
import java.util.UUID;

public interface ScreeningApi {
    UUID runScreening(ScreeningRequest req);
    ScreeningResultSummary getScreeningLog(UUID logId);
}
