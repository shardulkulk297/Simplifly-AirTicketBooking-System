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
@CrossOrigin(origins = "http://localhost:5173")
public class FlightController {

    private FlightService flightService;


    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @PostMapping("/api/flight/add")
    public ResponseEntity<?> addFlight(@RequestBody Flight flight, Principal principal){
        String username = principal.getName();
        return ResponseEntity.status(HttpStatus.CREATED).body(flightService.addFlight(flight, username));
    }

    @GetMapping("/api/flight/getAllFlights")
    public ResponseEntity<?> getAllFlights(Principal principal){

        String username = principal.getName();
        return ResponseEntity.status(HttpStatus.OK).body(flightService.getAllFlights(username));
    }

    @GetMapping
    public ResponseEntity<?> getFlightsByRoute(String origin, String destination){
        return ResponseEntity.status(HttpStatus.FOUND).body(flightService.getFlightsByRoute(origin, destination));
    }

    @GetMapping("/api/flight/getAll")
    public ResponseEntity<?> getAll(){
        return ResponseEntity.status(HttpStatus.FOUND).body(flightService.getAllFlightsForSearch());
    }

    @GetMapping("/api/flight/getById/{flightId}")
    public ResponseEntity<?> getFlightById(@PathVariable int flightId){
        return ResponseEntity.status(HttpStatus.FOUND).body(flightService.getFlight(flightId));
    }

    @PutMapping("/api/flight/update/{flightId}")
    public ResponseEntity<?> updateFlight(@RequestBody Flight flight, @PathVariable int flightId){
        return ResponseEntity.status(HttpStatus.CREATED).body(flightService.updateFlight(flight, flightId));
    }

    @DeleteMapping("/api/flight/delete/{flightId}")
    public ResponseEntity<?> deleteFlight(@PathVariable int flightId){
        flightService.deleteFlight(flightId);
        return ResponseEntity.status(HttpStatus.OK).body("Deleted Successfully");
    }

    @GetMapping("/api/flight/getTotalFlights")
    public ResponseEntity<?> getTotalFlights(){
        return ResponseEntity.status(HttpStatus.OK).body(flightService.getTotalFlights());
    }




}
