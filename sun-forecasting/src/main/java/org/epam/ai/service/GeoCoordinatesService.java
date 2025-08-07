package org.epam.ai.service;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GeoCoordinatesService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final static String OPEN_METEO_URL = "https://geocoding-api.open-meteo.com/v1/search?name=%s&count=1";

    @Tool("This method will fetch the coordinates of the given city")
    public String getCityCoordinates(@P("it is the name of city") String city) {
        String url = String.format(OPEN_METEO_URL, city);
        return restTemplate.getForObject(url, String.class);
    }
}
