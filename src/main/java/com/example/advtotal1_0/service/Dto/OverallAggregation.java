package com.example.advtotal1_0.service.Dto;

public class OverallAggregation {
    private int totalClicks;
    private double totalIncome;

    public OverallAggregation(int totalClicks, double totalIncome) {
        this.totalClicks = totalClicks;
        this.totalIncome = totalIncome;
    }

    public OverallAggregation() {
    }

    public int getTotalClicks() {
        return totalClicks;
    }

    public void setTotalClicks(int totalClicks) {
        this.totalClicks = totalClicks;
    }

    public double getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(double totalIncome) {
        this.totalIncome = totalIncome;
    }
}
