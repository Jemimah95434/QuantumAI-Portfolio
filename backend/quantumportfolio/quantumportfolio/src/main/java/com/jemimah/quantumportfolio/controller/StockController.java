package com.jemimah.quantumportfolio.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;

@RestController
@RequestMapping("/api/stocks")
@CrossOrigin(origins = "*")
public class StockController {

    private final String API_KEY = "demo"; // Replace with your Alpha Vantage API key
    private final String BASE_URL = "https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol=";

    @GetMapping("/{symbol}")
    public Map<String, Object> getStockPrice(@PathVariable String symbol) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String url = BASE_URL + symbol + "&apikey=" + API_KEY;

            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

            // Parse JSON into a properly typed Map<String, Object>
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(response.getBody(), new TypeReference<Map<String, Object>>() {});
        } catch (Exception e) {
            return Map.of("error", "Failed to fetch stock data: " + e.getMessage());
        }
    }
}
