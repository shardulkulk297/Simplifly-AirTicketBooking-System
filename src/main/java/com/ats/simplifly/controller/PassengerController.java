package com.ats.simplifly.controller;

import com.ats.simplifly.model.Passenger;
import com.ats.simplifly.service.PassengerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class PassengerController {

    private final PassengerService passengerService;

    public PassengerController(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    @PostMapping("/api/passenger/add")
    public ResponseEntity<?> addPassenger(@RequestBody Passenger passenger, Principal principal){
        return ResponseEntity.status(HttpStatus.CREATED).body(passengerService.addPassenger(passenger, principal.getName()));
    }



}
