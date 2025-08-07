package org.epam.ai.model;

import java.time.LocalDateTime;

public record SunriseSunsetForecast(
        String city,
        LocalDateTime sunrise,
        LocalDateTime sunset,
        String enhancedMessage
) {
}
