package com.ats.simplifly.model;

import jakarta.persistence.*;

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
    private int seatsPerRow;
    private int firstClassRows;
    private int businessClassRows;

    public int getBusinessClassRows() {
        return businessClassRows;
    }

    public void setBusinessClassRows(int businessClassRows) {
        this.businessClassRows = businessClassRows;
    }

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



    public int getSeatsPerRow() {
        return seatsPerRow;
    }

    public void setSeatsPerRow(int seatsPerRow) {
        this.seatsPerRow = seatsPerRow;
    }

    public int getFirstClassRows() {
        return firstClassRows;
    }

    public void setFirstClassRows(int firstClassRows) {
        this.firstClassRows = firstClassRows;
    }


}
