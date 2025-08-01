package org.epam.ai.service;


import org.epam.ai.client.GeocodingClient;
import org.epam.ai.dto.GeoLocation;
import org.epam.ai.dto.OpenMeteoResponse;
import org.epam.ai.exception.InvalidCityException;
import org.epam.ai.model.SunForecastResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;
import java.util.Optional;

@Service
public class SunForecastService {

    private final RestTemplate restTemplate;
    private final LangChainAiService langChainAiService;
    private final GeocodingClient geocodingClient;

    @Autowired
    public SunForecastService(RestTemplate restTemplate, LangChainAiService langChainAiService, GeocodingClient geocodingClient) {
        this.restTemplate = restTemplate;
        this.langChainAiService = langChainAiService;
        this.geocodingClient = geocodingClient;
    }


    public SunForecastResponse getSunForecast(String city) {
        // 1. Geocode city to lat/lon
        Optional<GeoLocation> locationOpt = geocodingClient.getLocation(city);
        if (locationOpt.isEmpty()) {
            throw new InvalidCityException("Invalid or unknown city: " + city);
        }
        GeoLocation location = locationOpt.get();
        double lat = location.getLatitude();
        double lon = location.getLongitude();

        // 2. Fetch sunrise/sunset from Open-Meteo
        Map<String, String> sunTimes = fetchSunTimes(lat, lon);

        // 3. Generate explanation
        String explanation = langChainAiService.generateExplanation(city, sunTimes.get("sunrise"), sunTimes.get("sunset"));

        return new SunForecastResponse(city, sunTimes.get("sunrise"), sunTimes.get("sunset"), explanation);
    }

    private Map<String, String> fetchSunTimes(double lat, double lon) {
        String url = UriComponentsBuilder.newInstance()
                .scheme("https")
                .host("api.open-meteo.com")
                .path("/v1/forecast")
                .queryParam("latitude", lat)
                .queryParam("longitude", lon)
                .queryParam("daily", "sunrise,sunset")
                .queryParam("timezone", "IST")
                .build()
                .toUriString();

        var response = restTemplate.getForObject(url, OpenMeteoResponse.class);
        if (response != null && response.daily != null && response.daily.sunrise != null && response.daily.sunset != null) {
            // Take the first (tomorrow's) sunrise/sunset
            String sunrise = response.daily.sunrise[0];
            String sunset = response.daily.sunset[0];
            return Map.of("sunrise", sunrise, "sunset", sunset);
        }
        throw new RuntimeException("Could not fetch sun times");
    }
}
