package com.ats.simplifly.dto;

import com.ats.simplifly.model.*;
import com.ats.simplifly.model.enums.BookingStatus;
import com.ats.simplifly.model.enums.SeatClassType;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

@Component
public class BookingDto {

    private int bookingId;
    private BookingStatus bookingStatus;
    private String bookedBy;
    private List<Passenger> passengerNames;
    private Route route;
    private String flightNumber;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private double totalPrice;
    private List<String> seatNumbers;

    public List<String> getSeatNumbers() {
        return seatNumbers;
    }

    public void setSeatNumbers(List<String> seatNumbers) {
        this.seatNumbers = seatNumbers;
    }

    public BookingStatus getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(BookingStatus bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public String getBookedBy() {
        return bookedBy;
    }

    public void setBookedBy(String bookedBy) {
        this.bookedBy = bookedBy;
    }

    public List<Passenger> getPassengerNames() {
        return passengerNames;
    }

    public void setPassengerNames(List<Passenger> passengerNames) {
        this.passengerNames = passengerNames;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
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

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof BookingDto that)) return false;
        return bookingId == that.bookingId && Double.compare(totalPrice, that.totalPrice) == 0 && bookingStatus == that.bookingStatus && Objects.equals(bookedBy, that.bookedBy) && Objects.equals(passengerNames, that.passengerNames) && Objects.equals(route, that.route) && Objects.equals(flightNumber, that.flightNumber) && Objects.equals(departureTime, that.departureTime) && Objects.equals(arrivalTime, that.arrivalTime) && Objects.equals(seatNumbers, that.seatNumbers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookingId, bookingStatus, bookedBy, passengerNames, route, flightNumber, departureTime, arrivalTime, totalPrice, seatNumbers);
    }
}
