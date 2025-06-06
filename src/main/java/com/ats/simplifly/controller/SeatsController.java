package com.ats.simplifly.controller;

import com.ats.simplifly.model.Seat;
import com.ats.simplifly.service.SeatService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SeatsController {

    private SeatService seatService;

    public SeatsController(SeatService seatService) {
        this.seatService = seatService;
    }

//    @PostMapping("/api/seats/add")
//    public ResponseEntity<?> bookSeats(@RequestBody Seat seat){
//        return ResponseEntity.status(HttpStatus.CREATED).body(seatService.bookSeats(seat));
//    }



}
