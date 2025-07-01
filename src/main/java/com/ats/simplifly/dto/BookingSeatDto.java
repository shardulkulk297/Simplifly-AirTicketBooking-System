package com.ats.simplifly.dto;

import java.util.Objects;

public class BookingSeatDto {
    private int id;
    private BookingDto booking;
    private PassengerDto passenger;
    private SeatDto seat;
    private double price;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BookingDto getBooking() {
        return booking;
    }

    public void setBooking(BookingDto booking) {
        this.booking = booking;
    }

    public PassengerDto getPassenger() {
        return passenger;
    }

    public void setPassenger(PassengerDto passenger) {
        this.passenger = passenger;
    }

    public SeatDto getSeat() {
        return seat;
    }

    public void setSeat(SeatDto seat) {
        this.seat = seat;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof BookingSeatDto that)) return false;
        return id == that.id && Double.compare(price, that.price) == 0 && Objects.equals(booking, that.booking) && Objects.equals(passenger, that.passenger) && Objects.equals(seat, that.seat);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, booking, passenger, seat, price);
    }
}
