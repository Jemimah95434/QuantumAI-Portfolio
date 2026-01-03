package com.jemimah.quantumportfolio.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Map;

@RestController
public class AnalyticsController {

    @GetMapping("/api/analytics")
    public List<Map<String, Object>> getAnalytics() {
        return List.of(
            Map.of("label", "Stock A", "predictedReturn", 10),
            Map.of("label", "Stock B", "predictedReturn", -5),
            Map.of("label", "Crypto X", "predictedReturn", 25)
        );
    }
}

