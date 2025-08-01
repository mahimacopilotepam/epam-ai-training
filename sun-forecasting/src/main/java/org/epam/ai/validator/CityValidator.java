package org.epam.ai.validator;

import org.epam.ai.client.GeocodingClient;
import org.epam.ai.exception.InvalidCityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CityValidator {

    private final GeocodingClient geocodingClient;

    @Autowired
    public CityValidator(GeocodingClient geocodingClient) {
        this.geocodingClient = geocodingClient;
    }

    public void validate(String city) {
        if (city == null || city.trim().isEmpty()) {
            throw new IllegalArgumentException("City name must not be empty");
        }
        // Basic invalid city name check: only letters, spaces, and hyphens allowed
        if (!city.matches("^[a-zA-Z\\s\\-]+$")) {
            throw new InvalidCityException("City name contains invalid characters");
        }
        // Check if city exists using geocoding API
        if (geocodingClient.getLocation(city.trim()).isEmpty()) {
            throw new InvalidCityException("City does not exist in the world: " + city);
        }
    }
}
