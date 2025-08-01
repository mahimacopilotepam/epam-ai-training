package org.epam.ai.dto;

public class SunTimes {
    private String sunrise;
    private String sunset;

    public SunTimes(String sunrise, String sunset) {
        this.sunrise = sunrise;
        this.sunset = sunset;
    }

    public String getSunrise() {
        return sunrise;
    }

    public String getSunset() {
        return sunset;
    }
}

