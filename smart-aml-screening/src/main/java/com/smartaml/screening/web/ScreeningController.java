package com.smartaml.screening.web;

import com.smartaml.screening.api.ScreeningApi;
import com.smartaml.screening.api.dto.ScreeningRequest;
import com.smartaml.screening.api.dto.ScreeningResultSummary;
import com.smartaml.shared.response.ApiResponse;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/screening")
public class ScreeningController {

    private final ScreeningApi screeningApi;

    public ScreeningController(ScreeningApi screeningApi) { this.screeningApi = screeningApi; }

    @PostMapping("/run")
    public ApiResponse<UUID> run(@RequestBody ScreeningRequest req) {
        UUID id = screeningApi.runScreening(req);
        return ApiResponse.created(id);
    }

    @GetMapping("/logs/{id}")
    public ApiResponse<ScreeningResultSummary> log(@PathVariable UUID id) {
        ScreeningResultSummary s = screeningApi.getScreeningLog(id);
        return ApiResponse.ok(s);
    }
}
