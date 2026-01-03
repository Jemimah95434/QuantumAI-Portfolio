package com.jemimah.quantumportfolio.controller;

import com.jemimah.quantumportfolio.model.Portfolio;
import com.jemimah.quantumportfolio.model.QuantumPrediction;
import com.jemimah.quantumportfolio.model.QuantumPredictionResponse;
import com.jemimah.quantumportfolio.repository.PortfolioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

/**
 * Controller for Quantum AI Predictions with live stock/crypto prices
 */
@RestController
@RequestMapping("/api/quantum-ai")
@CrossOrigin(origins = "*") // allow frontend
public class QuantumAiController {

    @Autowired
    private PortfolioRepository portfolioRepo;

    private final RestTemplate restTemplate = new RestTemplate();

    // Replace this with your free Alpha Vantage API key
    private static final String ALPHA_VANTAGE_KEY = "ORDCDCA7OM4OIBCD";

    // Map portfolio names to stock/crypto symbols
    private static final Map<String, String> stockMap = Map.of(
            "Apple Inc.", "AAPL",
            "Tesla", "TSLA",
            "Amazon", "AMZN",
            "Microsoft", "MSFT"
    );

    private static final Map<String, String> cryptoMap = Map.of(
            "AI Innovation Fund", "BTC-USD" // example crypto symbol
    );

    @GetMapping("/predict")
    public QuantumPredictionResponse getQuantumPredictions() {
        List<Portfolio> portfolios = portfolioRepo.findAll();
        List<QuantumPrediction> predictions = new ArrayList<>();
        double totalPredictedValue = 0;
        double totalCurrentValue = 0;

        long currentTime = System.currentTimeMillis();
        Random rand = new Random();

        for (Portfolio p : portfolios) {
            // Fetch live price
            double currentPrice = fetchLivePrice(p.getName(), p.getCurrentValue());
            totalCurrentValue += currentPrice;

            // AI-simulated predicted return
            double quantumFactor = Math.sin(currentTime / 100000.0) * 5;
            double predictedReturn = -5 + rand.nextDouble() * 15 + quantumFactor;
            predictedReturn = Math.round(predictedReturn * 100.0) / 100.0;

            // Predicted value = currentPrice * (1 + predictedReturn / 100)
            double predictedValue = currentPrice * (1 + predictedReturn / 100.0);
            predictedValue = Math.round(predictedValue * 100.0) / 100.0;
            totalPredictedValue += predictedValue;

            // Add prediction object
            predictions.add(new QuantumPrediction(
                    p.getName(),
                    currentPrice,   // pass currentPrice
                    predictedReturn,
                    predictedValue
            ));
        }

        // Round totals
        totalPredictedValue = Math.round(totalPredictedValue * 100.0) / 100.0;
        totalCurrentValue = Math.round(totalCurrentValue * 100.0) / 100.0;

        return new QuantumPredictionResponse(predictions, totalPredictedValue, totalCurrentValue);
    }

    /**
     * Fetch live price for stock/crypto from Alpha Vantage
     */
    private double fetchLivePrice(String portfolioName, double fallbackValue) {
        try {
            String symbol = stockMap.getOrDefault(portfolioName, cryptoMap.get(portfolioName));
            if (symbol == null) return fallbackValue;

            String url;
            boolean isCrypto = cryptoMap.containsKey(portfolioName);

            if (isCrypto) {
                url = "https://www.alphavantage.co/query?function=CURRENCY_EXCHANGE_RATE"
                        + "&from_currency=" + symbol.split("-")[0]
                        + "&to_currency=" + symbol.split("-")[1]
                        + "&apikey=" + ALPHA_VANTAGE_KEY;

                Object rawResponse = restTemplate.getForObject(url, Object.class);
                if (!(rawResponse instanceof Map)) return fallbackValue;

                Map<?, ?> response = (Map<?, ?>) rawResponse;
                Map<?, ?> exchangeRate = (Map<?, ?>) response.get("Realtime Currency Exchange Rate");
                if (exchangeRate == null) return fallbackValue;

                String priceStr = (String) exchangeRate.get("5. Exchange Rate");
                return Double.parseDouble(priceStr);

            } else {
                url = "https://www.alphavantage.co/query?function=GLOBAL_QUOTE"
                        + "&symbol=" + symbol
                        + "&apikey=" + ALPHA_VANTAGE_KEY;

                Object rawResponse = restTemplate.getForObject(url, Object.class);
                if (!(rawResponse instanceof Map)) return fallbackValue;

                Map<?, ?> response = (Map<?, ?>) rawResponse;
                Map<?, ?> quote = (Map<?, ?>) response.get("Global Quote");
                if (quote == null) return fallbackValue;

                String priceStr = (String) quote.get("05. price");
                return Double.parseDouble(priceStr);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return fallbackValue;
        }
    }
}
