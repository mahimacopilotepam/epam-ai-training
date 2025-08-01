package org.epam.ai.dto;

public class OpenMeteoResponse {
    public Daily daily;

    public static class Daily {
        public String[] sunrise;
        public String[] sunset;
    }
}

