package org.epam.ai.client;


import org.epam.ai.dto.GeoLocation;
import org.epam.ai.dto.OpenMeteoResponse;
import org.epam.ai.dto.SunTimes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class OpenMeteoClient {

    private final RestTemplate restTemplate;

    @Autowired
    public OpenMeteoClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public SunTimes getSunTimes(GeoLocation location) {
        String url = String.format(
                "https://api.open-meteo.com/v1/forecast?latitude=%s&longitude=%s&daily=sunrise,sunset&timezone=IST",
                location.getLatitude(),
                location.getLongitude()
        );

        OpenMeteoResponse response = restTemplate.getForObject(url, OpenMeteoResponse.class);
        if (response != null && response.daily != null && response.daily.sunrise != null && response.daily.sunset != null
                && response.daily.sunrise.length > 0 && response.daily.sunset.length > 0) {
            return new SunTimes(response.daily.sunrise[0], response.daily.sunset[0]);
        }
        throw new RuntimeException("Could not fetch sun times from Open-Meteo");
    }
}
