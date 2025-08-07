package org.epam.ai.controller;



import org.epam.ai.model.SunriseSunsetForecast;
import org.epam.ai.service.ForecastAIAssistant;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class SunForecastController {
    private final org.epam.ai.service.ForecastAIAssistant forecastAIAssistant;

    public SunForecastController(ForecastAIAssistant forecastAIAssistant) {
        this.forecastAIAssistant = forecastAIAssistant;
    }

    @GetMapping("/sun-forecast")
    public ResponseEntity<SunriseSunsetForecast> getTodaySunriseSunset(@RequestParam(name = "city") String city) {
        return new ResponseEntity<>(forecastAIAssistant.askSunForecast(city), HttpStatus.OK);
    }
}
