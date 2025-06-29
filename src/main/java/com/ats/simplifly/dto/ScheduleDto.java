package com.ats.simplifly.dto;

import java.time.LocalDateTime;

public class ScheduleDto {
    private int id;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private double fare;
    private String isWifiAvailable;
    private String freeMeal;
    private String mealAvailable;
    private Double businessClassRate;
    private Double firstClassRate;
    private FlightDto flight;

    public ScheduleDto() {}

    public ScheduleDto(int id, LocalDateTime departureTime, LocalDateTime arrivalTime, double fare,
                       String isWifiAvailable, String freeMeal, String mealAvailable,
                       Double businessClassRate, Double firstClassRate, FlightDto flight) {
        this.id = id;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.fare = fare;
        this.isWifiAvailable = isWifiAvailable;
        this.freeMeal = freeMeal;
        this.mealAvailable = mealAvailable;
        this.businessClassRate = businessClassRate;
        this.firstClassRate = firstClassRate;
        this.flight = flight;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public double getFare() {
        return fare;
    }

    public void setFare(double fare) {
        this.fare = fare;
    }

    public String getIsWifiAvailable() {
        return isWifiAvailable;
    }

    public void setIsWifiAvailable(String isWifiAvailable) {
        this.isWifiAvailable = isWifiAvailable;
    }

    public String getFreeMeal() {
        return freeMeal;
    }

    public void setFreeMeal(String freeMeal) {
        this.freeMeal = freeMeal;
    }

    public String getMealAvailable() {
        return mealAvailable;
    }

    public void setMealAvailable(String mealAvailable) {
        this.mealAvailable = mealAvailable;
    }

    public Double getBusinessClassRate() {
        return businessClassRate;
    }

    public void setBusinessClassRate(Double businessClassRate) {
        this.businessClassRate = businessClassRate;
    }

    public Double getFirstClassRate() {
        return firstClassRate;
    }

    public void setFirstClassRate(Double firstClassRate) {
        this.firstClassRate = firstClassRate;
    }

    public FlightDto getFlight() {
        return flight;
    }

    public void setFlight(FlightDto flight) {
        this.flight = flight;
    }
}
