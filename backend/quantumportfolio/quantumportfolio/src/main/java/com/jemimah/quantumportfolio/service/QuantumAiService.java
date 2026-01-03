package com.jemimah.quantumportfolio.service;

import com.jemimah.quantumportfolio.model.Portfolio;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class QuantumAiService {

    public Map<String, Object> generatePredictions(List<Portfolio> portfolios) {
        List<Map<String, Object>> predictions = new ArrayList<>();
        double totalPredicted = 0;

        Random rand = new Random();
        for (Portfolio p : portfolios) {
            double volatility = rand.nextDouble() * 0.05; // up to 5% random factor
            double aiTrendFactor = Math.sin(System.currentTimeMillis() / 1000000.0);
            double predictedGrowth = (p.getCurrentValue() * (1 + volatility + aiTrendFactor / 10));

            Map<String, Object> stockPrediction = new HashMap<>();
            stockPrediction.put("name", p.getName());
            stockPrediction.put("currentValue", p.getCurrentValue());
            stockPrediction.put("predictedValue", Math.round(predictedGrowth * 100.0) / 100.0);

            predictions.add(stockPrediction);
            totalPredicted += predictedGrowth;
        }

        Map<String, Object> result = new HashMap<>();
        result.put("predictions", predictions);
        result.put("totalPredictedValue", Math.round(totalPredicted * 100.0) / 100.0);
        return result;
    }
}


