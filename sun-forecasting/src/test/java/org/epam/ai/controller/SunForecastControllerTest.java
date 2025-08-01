// SunForecastControllerTest.java
package org.epam.ai.controller;

import org.epam.ai.exception.InvalidCityException;
import org.epam.ai.model.SunForecastResponse;
import org.epam.ai.service.SunForecastService;
import org.epam.ai.validator.CityValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@WebMvcTest(SunForecastController.class)
class SunForecastControllerTest {

    private SunForecastService sunForecastService;
    private CityValidator cityValidator;
    private SunForecastController controller;

    @BeforeEach
    void setUp() {
        sunForecastService = mock(SunForecastService.class);
        cityValidator = mock(CityValidator.class);
        controller = new SunForecastController(sunForecastService, cityValidator);
    }

    @Test
    void testGetSunTimes_ValidCity_ReturnsOk() {
        String city = "Paris";
        SunForecastResponse response = new SunForecastResponse();
        doNothing().when(cityValidator).validate(city);
        when(sunForecastService.getSunForecast(city)).thenReturn(response);

        ResponseEntity<?> result = controller.getSunTimes(city);

        assertEquals(response, result.getBody());
    }

    @Test
    void testGetSunTimes_InvalidCity_ReturnsBadRequest() {
        String city = "InvalidCity";
        doThrow(new InvalidCityException("Invalid city")).when(cityValidator).validate(city);

        ResponseEntity<?> result = controller.getSunTimes(city);

        assertTrue(result.getBody().toString().contains("Invalid city"));
    }

    @Test
    void testGetSunTimes_InternalError_ReturnsServerError() {
        String city = "Paris";
        doNothing().when(cityValidator).validate(city);
        when(sunForecastService.getSunForecast(city)).thenThrow(new RuntimeException());

        ResponseEntity<?> result = controller.getSunTimes(city);

        assertTrue(result.getBody().toString().contains("Internal server error"));
    }
}