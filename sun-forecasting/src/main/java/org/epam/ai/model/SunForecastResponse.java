package org.epam.ai.model;

public class SunForecastResponse {
    private String city;
    private String sunrise;
    private String sunset;
    private String explanation;

    // Constructors, getters, setters

    public SunForecastResponse() {}

    public SunForecastResponse(String city, String sunrise, String sunset, String explanation) {
        this.city = city;
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.explanation = explanation;
    }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getSunrise() { return sunrise; }
    public void setSunrise(String sunrise) { this.sunrise = sunrise; }

    public String getSunset() { return sunset; }
    public void setSunset(String sunset) { this.sunset = sunset; }

    public String getExplanation() { return explanation; }
    public void setExplanation(String explanation) { this.explanation = explanation; }
}

