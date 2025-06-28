package com.ats.simplifly.model;

import com.ats.simplifly.model.enums.DaysOfWeek;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "schedule")
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    private Flight flight;
    @Column(name = "departure_time", nullable = false)
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private double fare;
    private String isWifiAvailable;
    private String freeMeal;
    private String mealAvailable;
    @Column(name = "business_class_rate")
    private Double businessClassRate;

    @Column(name = "first_class_rate")
    private Double firstClassRate;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
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

}