package com.ats.simplifly.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "flight")
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    private FlightOwner owner;
    @ManyToOne
    private Route route;
    @Column(name = "flight_number", nullable = false)
    private String flightNumber;
    private int baggageCheckin;
    private int baggageCabin;
    private int totalSeats;
    private int firstClassSeats;
    private int businessClassSeats;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public FlightOwner getOwner() {
        return owner;
    }

    public void setOwner(FlightOwner owner) {
        this.owner = owner;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public int getBaggageCheckin() {
        return baggageCheckin;
    }

    public void setBaggageCheckin(int baggageCheckin) {
        this.baggageCheckin = baggageCheckin;
    }

    public int getBaggageCabin() {
        return baggageCabin;
    }

    public void setBaggageCabin(int baggageCabin) {
        this.baggageCabin = baggageCabin;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(int totalSeats) {
        this.totalSeats = totalSeats;
    }


    public int getFirstClassSeats() {
        return firstClassSeats;
    }

    public void setFirstClassSeats(int firstClassSeats) {
        this.firstClassSeats = firstClassSeats;
    }

    public int getBusinessClassSeats() {
        return businessClassSeats;
    }

    public void setBusinessClassSeats(int businessClassSeats) {
        this.businessClassSeats = businessClassSeats;
    }


}
