package com.ats.simplifly.service;

import com.ats.simplifly.model.Booking;
import com.ats.simplifly.model.BookingSeat;
import com.ats.simplifly.model.Passenger;
import com.ats.simplifly.model.Seat;
import com.ats.simplifly.repository.BookingRepository;
import com.ats.simplifly.repository.BookingSeatRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class BookingSeatService {

    private final BookingRepository bookingRepository;
    private final BookingSeatRepository bookingSeatRepository;

    public BookingSeatService(BookingRepository bookingRepository, BookingSeatRepository bookingSeatRepository) {
        this.bookingRepository = bookingRepository;
        this.bookingSeatRepository = bookingSeatRepository;
    }

    public List<BookingSeat> addBookingSeat(Map<Passenger, Seat> passengers, Booking booking){

        BookingSeat bookingSeat = new BookingSeat();
        List<BookingSeat> bookingSeats = new ArrayList<>();
        for(Map.Entry<Passenger, Seat> entry: passengers.entrySet()){
            Passenger passenger = entry.getKey();
            Seat seat = entry.getValue();
            bookingSeat.setPassenger(passenger);
            bookingSeat.setBooking(booking);
            bookingSeat.setSeat(seat);
            bookingSeat.setPrice(seat.getPrice());
            BookingSeat bookingSeatToSave = bookingSeatRepository.save(bookingSeat);
            bookingSeats.add(bookingSeat);
        }
        return bookingSeats;

    }

}
