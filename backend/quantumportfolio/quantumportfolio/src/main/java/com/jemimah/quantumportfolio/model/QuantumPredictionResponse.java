package com.jemimah.quantumportfolio.model;

import java.util.List;

public class QuantumPredictionResponse {
    private List<QuantumPrediction> predictions;
    private double totalPredictedValue;
    private double totalCurrentValue; // <-- new

    public QuantumPredictionResponse(List<QuantumPrediction> predictions, double totalPredictedValue, double totalCurrentValue) {
        this.predictions = predictions;
        this.totalPredictedValue = totalPredictedValue;
        this.totalCurrentValue = totalCurrentValue; // <-- new
    }

    // Getters and setters
    public List<QuantumPrediction> getPredictions() { return predictions; }
    public void setPredictions(List<QuantumPrediction> predictions) { this.predictions = predictions; }

    public double getTotalPredictedValue() { return totalPredictedValue; }
    public void setTotalPredictedValue(double totalPredictedValue) { this.totalPredictedValue = totalPredictedValue; }

    public double getTotalCurrentValue() { return totalCurrentValue; } // <-- new
    public void setTotalCurrentValue(double totalCurrentValue) { this.totalCurrentValue = totalCurrentValue; }
}
