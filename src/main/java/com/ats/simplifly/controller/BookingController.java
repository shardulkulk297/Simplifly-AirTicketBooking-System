package com.ats.simplifly.controller;

import com.ats.simplifly.dto.BookingDto;
import com.ats.simplifly.dto.BookingRequestDto;
import jakarta.persistence.Column;
import org.springframework.http.HttpStatus;

import com.ats.simplifly.model.Booking;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ats.simplifly.service.BookingService;

import java.security.Principal;

@RestController
@CrossOrigin("http://localhost:5173")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/api/booking/book")
    public ResponseEntity<?> bookFlight(@RequestBody BookingRequestDto bookingRequestDto, Principal principal){
        return ResponseEntity.status(HttpStatus.CREATED).body(bookingService.bookTickets(bookingRequestDto, principal.getName()));
    }

    @GetMapping("/api/booking/getBookings")
    public ResponseEntity<?> getBookings(Principal principal){
        return ResponseEntity.status(HttpStatus.OK).body(bookingService.getBookings(principal.getName()));
    }
}
