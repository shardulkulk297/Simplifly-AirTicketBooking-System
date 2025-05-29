package com.ats.simplifly.controller;

import com.ats.simplifly.model.Flight;
import com.ats.simplifly.model.Route;
import com.ats.simplifly.service.FlightService;
import com.ats.simplifly.service.RouteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FlightController {

    private final FlightService flightService;
    private final RouteService routeService;

    public FlightController(FlightService flightService, RouteService routeService) {
        this.flightService = flightService;
        this.routeService = routeService;
    }

    @PostMapping("/api/flight/add")
    public ResponseEntity<?> addFlight(@RequestBody Flight flight){
        return ResponseEntity.status(HttpStatus.CREATED).body(flightService.addFlight(flight));
    }

    @PostMapping("/api/flight/route/add")
    public ResponseEntity<?> addRoute(@RequestBody Route route){
        return ResponseEntity.status(HttpStatus.CREATED).body(routeService.addRoute(route));
    }

}
