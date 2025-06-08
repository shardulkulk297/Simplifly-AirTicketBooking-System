package com.ats.simplifly.controller;

import com.ats.simplifly.dto.BookingRequestDto;
import org.springframework.http.HttpStatus;

import com.ats.simplifly.model.Booking;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ats.simplifly.service.BookingService;

import java.security.Principal;

@RestController
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/api/booking/book")
    public ResponseEntity<?> bookFlight(@RequestBody BookingRequestDto bookingRequestDto, Principal principal){
        return ResponseEntity.status(HttpStatus.CREATED).body(bookingService.bookTickets(bookingRequestDto, principal.getName()));
    }
}
