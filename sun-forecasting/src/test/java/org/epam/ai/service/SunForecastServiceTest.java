package org.epam.ai.service;


import org.epam.ai.client.GeocodingClient;
import org.epam.ai.client.LangChain4jClient;
import org.epam.ai.client.OpenMeteoClient;
import org.epam.ai.dto.GeoLocation;
import org.epam.ai.dto.GeocodingResponse;
import org.epam.ai.dto.GeocodingResult;
import org.epam.ai.dto.OpenMeteoResponse;
import org.epam.ai.exception.InvalidCityException;
import org.epam.ai.model.SunForecastResponse;
import org.epam.ai.validator.CityValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SunForecastServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private CityValidator cityValidator;

    private GeocodingClient geocodingClient;
    private OpenMeteoClient openMeteoClient;

    @Mock
    private LangChain4jClient langChain4jClient;

    @Mock
    private LangChainAiService langChainAiService;

    @InjectMocks
    private SunForecastService sunForecastService;

    @BeforeEach
    void setUp() {
        geocodingClient = new GeocodingClient(restTemplate);
        openMeteoClient = new OpenMeteoClient(restTemplate);
        sunForecastService = new SunForecastService(
                restTemplate, langChainAiService, geocodingClient
        );
    }

    @Test
    void whenCityNotFound_thenThrowsInvalidCityException() {
        when(geocodingClient.getLocation("Atlantis")).thenReturn(Optional.empty());
        assertThrows(InvalidCityException.class, () -> sunForecastService.getSunForecast("Atlantis"));
    }

    @Test
    void whenValidCity_thenReturnsForecastWithNaturalExplanation() {
        GeoLocation location = new GeoLocation(48.8566, 2.3522, "Paris");

        // Mock geocoding API response
        GeocodingResponse geoResponse = new GeocodingResponse();
        GeocodingResult geoResult = new GeocodingResult();
        geoResult.latitude = 48.8566;
        geoResult.longitude = 2.3522;
        geoResult.name = "Paris";
        geoResponse.results = new GeocodingResult[]{geoResult};
        when(restTemplate.getForObject(
                anyString(),
                eq(GeocodingResponse.class)
        )).thenReturn(geoResponse);

        // Mock Open-Meteo API response
        OpenMeteoResponse openMeteoResponse = new OpenMeteoResponse();
        OpenMeteoResponse.Daily daily = new OpenMeteoResponse.Daily();
        daily.sunrise = new String[]{"2024-06-10T07:12:00+05:30"};
        daily.sunset = new String[]{"2024-06-10T17:49:00+05:30"};
        openMeteoResponse.daily = daily;
        when(restTemplate.getForObject(
                anyString(),
                eq(OpenMeteoResponse.class)
        )).thenReturn(openMeteoResponse);

        when(langChainAiService.generateExplanation(anyString(), anyString(), anyString()))
                .thenReturn("In Paris, the sun will rise tomorrow at 7:12 AM IST and set at 5:49 PM IST — a perfect time to enjoy the golden hour!");

        SunForecastResponse response = sunForecastService.getSunForecast("Paris");

        assertEquals("Paris", response.getCity());
        assertTrue(response.getExplanation().toLowerCase().contains("paris"));
        assertTrue(response.getExplanation().contains("sun will rise"));
        assertTrue(response.getExplanation().contains("sun will rise tomorrow at 7:12 AM IST"));
    }
}
