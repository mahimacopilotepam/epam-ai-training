package org.epam.ai.controller;


import org.epam.ai.exception.InvalidCityException;
import org.epam.ai.model.SunForecastResponse;
import org.epam.ai.service.SunForecastService;
import org.epam.ai.validator.CityValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/sun-forecast")
public class SunForecastController {

    private final SunForecastService sunForecastService;
    private final CityValidator cityValidator;

    @Autowired
    public SunForecastController(SunForecastService sunForecastService, CityValidator cityValidator) {
        this.sunForecastService = sunForecastService;
        this.cityValidator = cityValidator;
    }

    @GetMapping
    public ResponseEntity<?> getSunTimes(@RequestParam String city) {
        try {
            cityValidator.validate(city);
            SunForecastResponse response = sunForecastService.getSunForecast(city.trim());
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException | InvalidCityException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Internal server error"));
        }
    }
}
