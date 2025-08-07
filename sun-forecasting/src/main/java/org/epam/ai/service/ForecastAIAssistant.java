package org.epam.ai.service;


import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.spring.AiService;
import org.epam.ai.model.SunriseSunsetForecast;

@AiService
public interface ForecastAIAssistant {
    @SystemMessage("You are a helpful assistant that gives sunrise/sunset info in a friendly way. Strictly show sunrise and sunset time in IST timezone")
    SunriseSunsetForecast askSunForecast(String city);
}
