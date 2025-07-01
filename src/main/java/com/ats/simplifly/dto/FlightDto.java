package com.ats.simplifly.dto;

import com.ats.simplifly.model.Route;

import java.util.Objects;

public class FlightDto {
    private int id;
    private String flightNumber;
    private int baggageCheckin;
    private int baggageCabin;
    private int totalSeats;
    private int firstClassSeats;
    private int businessClassSeats;
    private Route route;
    private FlightOwnerDto owner;

    public FlightDto() {}

    public FlightDto(int id, String flightNumber, int baggageCheckin, int baggageCabin,
                     int totalSeats, int firstClassSeats, int businessClassSeats,
                     Route route, FlightOwnerDto owner) {
        this.id = id;
        this.flightNumber = flightNumber;
        this.baggageCheckin = baggageCheckin;
        this.baggageCabin = baggageCabin;
        this.totalSeats = totalSeats;
        this.firstClassSeats = firstClassSeats;
        this.businessClassSeats = businessClassSeats;
        this.route = route;
        this.owner = owner;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
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

    public Route getRoute() {
        return route;
    }
    public void setRoute(Route route) {
        this.route = route;
    }

    public FlightOwnerDto getOwner() {
        return owner;
    }
    public void setOwner(FlightOwnerDto owner) {
        this.owner = owner;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof FlightDto flightDto)) return false;
        return id == flightDto.id && baggageCheckin == flightDto.baggageCheckin && baggageCabin == flightDto.baggageCabin && totalSeats == flightDto.totalSeats && firstClassSeats == flightDto.firstClassSeats && businessClassSeats == flightDto.businessClassSeats && Objects.equals(flightNumber, flightDto.flightNumber) && Objects.equals(route, flightDto.route) && Objects.equals(owner, flightDto.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, flightNumber, baggageCheckin, baggageCabin, totalSeats, firstClassSeats, businessClassSeats, route, owner);
    }


}
