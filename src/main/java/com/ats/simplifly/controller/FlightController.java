package com.ats.simplifly.controller;

import com.ats.simplifly.model.Flight;
import com.ats.simplifly.model.Route;
import com.ats.simplifly.service.FlightService;
import com.ats.simplifly.service.RouteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
public class FlightController {

    private FlightService flightService;


    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @PostMapping("/api/flight/add")
    public ResponseEntity<?> addFlight(@RequestBody Flight flight){
        return ResponseEntity.status(HttpStatus.CREATED).body(flightService.addFlight(flight));
    }

    @GetMapping("/api/flight/getAll")
    public ResponseEntity<?> getAllFlights(Principal principal){

        String username = principal.getName();

        return ResponseEntity.status(HttpStatus.FOUND).body(flightService.getAllFlights(username));
    }

    @GetMapping("/api/flight/getById/{flightId}")
    public ResponseEntity<?> getFlightById(int flightId){
        return ResponseEntity.status(HttpStatus.FOUND).body(flightService.getFlight(flightId));
    }

    @PutMapping("/api/flight/update")
    public ResponseEntity<?> updateFlight(@RequestBody Flight flight, Principal principal){
        return ResponseEntity.status(HttpStatus.CREATED).body(flightService.updateFlight(flight,principal.getName()));
    }

    @DeleteMapping("/api/flight/delete/{flightId}")
    public ResponseEntity<?> deleteFlight(@PathVariable int flightId){
        flightService.deleteFlight(flightId);
        return ResponseEntity.status(HttpStatus.OK).body("Deleted Successfully");
    }




}
