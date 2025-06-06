package com.ats.simplifly.service;

import com.ats.simplifly.model.Booking;
import com.ats.simplifly.model.Seat;
import com.ats.simplifly.model.enums.BookingStatus;
import com.ats.simplifly.model.enums.SeatStatus;
import com.ats.simplifly.repository.BookingRepository;
import com.ats.simplifly.repository.SeatRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookingService {

    private final SeatService seatService;
    private final SeatRepository seatRepository;
    private BookingRepository bookingRepository;

    public BookingService(BookingRepository bookingRepository, SeatService seatService, SeatRepository seatRepository) {
        this.bookingRepository = bookingRepository;
        this.seatService = seatService;
        this.seatRepository = seatRepository;
    }



    public Booking bookTickets(Booking booking){
        booking.setBookingStatus(BookingStatus.PENDING);
        return null;
    }
}
