package com.ats.simplifly.controller;

import com.ats.simplifly.model.Schedule;
import com.ats.simplifly.model.Seat;
import com.ats.simplifly.service.SeatService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:5173")
public class SeatsController {

    private SeatService seatService;

    public SeatsController(SeatService seatService) {
        this.seatService = seatService;
    }

    @GetMapping("/api/flight/schedule/getSeats/{scheduleId}")
    public ResponseEntity<?> getSeats(@PathVariable int scheduleId){
        return ResponseEntity.status(HttpStatus.OK).body(seatService.getSeatsBySchedule(scheduleId));
    }

}
