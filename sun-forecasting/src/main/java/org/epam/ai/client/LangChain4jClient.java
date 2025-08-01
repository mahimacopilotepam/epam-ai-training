package org.epam.ai.client;

import org.springframework.stereotype.Component;

@Component
public class LangChain4jClient {

    // In a real implementation, this would call the LangChain4j API/service.
    public String generateExplanation(String city, String sunrise, String sunset) {
        // Example prompt and response
        return String.format(
            "In %s, the sun will rise tomorrow at %s and set at %s — a perfect time to enjoy the golden hour!",
            city, formatTime(sunrise), formatTime(sunset)
        );
    }

    private String formatTime(String isoDateTime) {
        // Simple formatting: extract time part for demonstration
        // In production, use a proper date-time formatter and timezone handling
        if (isoDateTime == null || !isoDateTime.contains("T")) return isoDateTime;
        String time = isoDateTime.split("T")[1];
        if (time.contains("+")) time = time.substring(0, time.indexOf('+'));
        if (time.contains("-")) time = time.substring(0, time.indexOf('-'));
        return time;
    }
}

