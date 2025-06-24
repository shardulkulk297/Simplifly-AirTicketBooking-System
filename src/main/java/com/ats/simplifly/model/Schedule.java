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
    private LocalTime departureTime;
    private LocalTime arrivalTime;
    private double fare;
    private String isWifiAvailable;
    private String freeMeal;
    private String mealAvailable;
    private LocalDate startDate;
    private LocalDate endDate;
    @ElementCollection(targetClass = DaysOfWeek.class)
    @CollectionTable(
            name = "operating_days",
            joinColumns = @JoinColumn(name = "schedule_id")
    )
    @Enumerated(EnumType.STRING)
    private Set<DaysOfWeek> operatingDays;
    @Column()
    private String status = "ACTIVE";

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalTime departureTime) {
        this.departureTime = departureTime;
    }

    public LocalTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalTime arrivalTime) {
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

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;

    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Set<DaysOfWeek> getOperatingDays() {
        return operatingDays;
    }

    public void setOperatingDays(Set<DaysOfWeek> operatingDays) {
        this.operatingDays = operatingDays;
    }
}