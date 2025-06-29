package com.ats.simplifly.controller;

import com.ats.simplifly.model.Schedule;
import com.ats.simplifly.service.ScheduleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping("/api/flight/schedule/add")
    public ResponseEntity<?> scheduleFlight(@RequestBody Schedule schedule){
        return ResponseEntity.status(HttpStatus.CREATED).body(scheduleService.scheduleFlight(schedule));
    }

    @PutMapping("/api/flight/schedule/update/{scheduleId}")
    public ResponseEntity<?> updateSchedule(@RequestBody Schedule schedule, @PathVariable int scheduleId){
        return ResponseEntity.status(HttpStatus.CREATED).body(scheduleService.updateSchedule(schedule, scheduleId));
    }

    /*
    API: Will return the flights scheduled by loggedIn flightOwner
    AUTH: FLIGHTOWNER
     */
    @GetMapping("/api/flight/schedule/getAll")
    public ResponseEntity<?> getAllSchedules(Principal principal){
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.getAllSchedules(principal.getName()));
    }
    /*
    API: For Filter according to price of the flight and the route
    SEARCH
    AUTHORITY: ALL
     */

    @GetMapping("/api/flight/schedule/getFlightsByFareAndRoute")
    public ResponseEntity<?> getFlightsByFareAndRoute(String origin, String destination, double fare){
        return ResponseEntity.status(HttpStatus.FOUND).body(scheduleService.getFlightsByFareAndRoute(origin, destination, fare));
    }
    /*
    API FOR: Detailed Schedule of the Flight
    AUTHORITY: FLIGHTOWNER & MANAGER
     */

    @GetMapping("/api/flight/schedule/search")
    public ResponseEntity<?> getFlightsSearch(@RequestParam String origin,@RequestParam String destination, @RequestParam String date, @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                                              @RequestParam(name = "size", required = false, defaultValue = "1000000") Integer size ){
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.getFlightSearch(origin, destination, date, page, size));
    }


    @GetMapping("/api/flight/schedule/getFlightSchedule")
    public ResponseEntity<?> getFlightSchedule(@RequestParam int flightId){
        return ResponseEntity.status(HttpStatus.FOUND).body(scheduleService.getScheduleByFlight(flightId));
    }

    @GetMapping("/api/flight/schedule/getSchedule/{scheduleId}")
    public ResponseEntity<?> getSchedule(@PathVariable int scheduleId){
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.getSchedule(scheduleId));
    }

    /*
    API FOR: Showing all the schedules (Can only be called by manager)
    AUTH: MANAGER
     */


    @DeleteMapping("/api/flight/schedule/delete/{scheduleId}")
    public ResponseEntity<?> deleteSchedule(@PathVariable int scheduleId){
        scheduleService.deleteSchedule(scheduleId);
        return ResponseEntity.status(HttpStatus.OK).body("Deleted Successfully");
    }









}
