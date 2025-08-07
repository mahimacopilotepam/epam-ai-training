package controller;

// ...existing imports...

@RestController
@RequestMapping("/sun-forecast")
public class SunForecastController {

    // ...existing code...

    @PostMapping("/forecast")
    public ResponseEntity<ForecastResponse> getForecast(@RequestBody ForecastRequest request) {
        // ...existing code...

        // Remove any direct call to generateExplanation
        // The explanation will be set by the LLM via LangChain4j

        ForecastResponse response = new ForecastResponse();
        response.setForecast(forecast);
        // response.setExplanation(langChainAiService.generateExplanation(forecast)); // REMOVE THIS LINE

        // ...existing code...
        return ResponseEntity.ok(response);
    }

    // ...existing code...
}

