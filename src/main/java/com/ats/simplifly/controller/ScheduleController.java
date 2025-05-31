package com.ats.simplifly.controller;

import com.ats.simplifly.model.Schedule;
import com.ats.simplifly.service.ScheduleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping("/api/flight/schedule/add")
    public ResponseEntity<?> scheduleFlight(@RequestBody Schedule schedule){
        return ResponseEntity.status(HttpStatus.CREATED).body(scheduleService.scheduleFlight(schedule));
    }

    @PutMapping("/api/flight/schedule/update")
    public ResponseEntity<?> updateSchedule(@RequestBody Schedule schedule, Principal principal){
        return ResponseEntity.status(HttpStatus.CREATED).body(scheduleService.updateSchedule(schedule, principal.getName()));
    }

    @GetMapping("/api/flight/schedule/getAll")
    public ResponseEntity<?> getAllSchedules(){
        return ResponseEntity.status(HttpStatus.FOUND).body(scheduleService.getAllSchedules());
    }

    @GetMapping("/api/flight/schedule/getFlightSchedule/{flightId}")
    public ResponseEntity<?> getFlightSchedule(@PathVariable int flightId){
        return ResponseEntity.status(HttpStatus.FOUND).body(scheduleService.getScheduleByFlight(flightId));
    }

    @DeleteMapping("/api/flight/schedule/delete/{scheduleId}")
    public ResponseEntity<?> deleteSchedule(@PathVariable int scheduleId){
        scheduleService.deleteSchedule(scheduleId);
        return ResponseEntity.status(HttpStatus.OK).body("Deleted Successfully");
    }





}
