package org.epam.ai.client;

import org.epam.ai.dto.GeoLocation;
import org.epam.ai.dto.GeocodingResponse;
import org.epam.ai.dto.GeocodingResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Component
public class GeocodingClient {

    private final RestTemplate restTemplate;

    @Autowired
    public GeocodingClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Optional<GeoLocation> getLocation(String city) {

        String url = "https://geocoding-api.open-meteo.com/v1/search?name=" +
                java.net.URLEncoder.encode(city, java.nio.charset.StandardCharsets.UTF_8) +
                "&count=1";

        try {
            GeocodingResponse response = restTemplate.getForObject(url, GeocodingResponse.class);
            if (response != null && response.results != null && response.results.length > 0) {
                GeocodingResult result = response.results[0];
                return Optional.of(new GeoLocation(result.latitude, result.longitude, result.name));
            }
        } catch (Exception e) {
            // Log error if needed
        }
        return Optional.empty();
    }
}
