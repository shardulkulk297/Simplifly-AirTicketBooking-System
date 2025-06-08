package com.ats.simplifly.dto;

import com.ats.simplifly.model.Booking;
import com.ats.simplifly.model.BookingSeat;
import com.ats.simplifly.model.Passenger;
import com.ats.simplifly.model.Seat;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class BookingDto {


    private Booking booking;
    private List<BookingSeat> bookingSeats;

    public List<BookingSeat> getBookingSeats() {
        return bookingSeats;
    }

    public void setBookingSeats(List<BookingSeat> bookingSeats) {
        this.bookingSeats = bookingSeats;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }


}
