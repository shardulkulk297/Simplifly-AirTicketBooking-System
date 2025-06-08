package com.ats.simplifly.controller;

import com.ats.simplifly.model.Schedule;
import com.ats.simplifly.model.Seat;
import com.ats.simplifly.service.SeatService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SeatsController {

    private SeatService seatService;

    public SeatsController(SeatService seatService) {
        this.seatService = seatService;
    }

//    @PutMapping("/api/flight/schedule/chooseSeats")
//    public ResponseEntity<?> chooseSeats(Schedule schedule, List<String> seatNumbers){
//        return ResponseEntity.status(HttpStatus.CREATED).body(seatService.chooseSeats(schedule, seatNumbers));
//    }


}
