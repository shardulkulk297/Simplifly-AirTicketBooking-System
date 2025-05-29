package com.ats.simplifly.controller;

import com.ats.simplifly.model.FlightOwner;
import com.ats.simplifly.model.User;
import com.ats.simplifly.service.FlightOwnerService;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class FlightOwnerController {

    private final FlightOwnerService flightOwnerService;

    public FlightOwnerController(FlightOwnerService flightOwnerService) {
        this.flightOwnerService = flightOwnerService;
    }

    @PostMapping("/api/flightOwner/add")
    public ResponseEntity<?> addFlightOwner(@RequestBody FlightOwner flightOwner){
        return ResponseEntity.status(HttpStatus.CREATED).body(flightOwnerService.addOwner(flightOwner));
    }

    @GetMapping("/api/flightOwner/getOwner")
    public ResponseEntity<?> getFlightOwner(Principal principal){
        String username = principal.getName();
        return ResponseEntity.status(HttpStatus.FOUND).body(flightOwnerService.getOwner(username));
    }

    @PostMapping
    public ResponseEntity<?> editProfile(@RequestBody FlightOwner flightOwner, Principal principal){
        String username = principal.getName();
        User user = new User();
        user.setUsername(username);
        flightOwner.setUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(flightOwnerService.editProfile(flightOwner));
    }



}
