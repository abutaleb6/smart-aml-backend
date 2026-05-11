package com.smartaml.analytics.api.dto;

import java.time.LocalDate;

public record ScreeningMetrics(LocalDate date, long screeningsRun, long matchesFound) {}
