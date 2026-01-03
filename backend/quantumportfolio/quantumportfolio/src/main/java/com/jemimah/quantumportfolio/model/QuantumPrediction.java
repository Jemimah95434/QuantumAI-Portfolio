package com.jemimah.quantumportfolio.model;

public class QuantumPrediction {
    private String label;
    private double currentPrice;      // <-- new
    private double predictedReturn;
    private double predictedValue;

    public QuantumPrediction(String label, double currentPrice, double predictedReturn, double predictedValue) {
        this.label = label;
        this.currentPrice = currentPrice; // <-- new
        this.predictedReturn = predictedReturn;
        this.predictedValue = predictedValue;
    }

    // Getters and setters
    public String getLabel() { return label; }
    public void setLabel(String label) { this.label = label; }

    public double getCurrentPrice() { return currentPrice; } // <-- new
    public void setCurrentPrice(double currentPrice) { this.currentPrice = currentPrice; }

    public double getPredictedReturn() { return predictedReturn; }
    public void setPredictedReturn(double predictedReturn) { this.predictedReturn = predictedReturn; }

    public double getPredictedValue() { return predictedValue; }
    public void setPredictedValue(double predictedValue) { this.predictedValue = predictedValue; }
}
