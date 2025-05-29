package com.ats.simplifly.service;

import com.ats.simplifly.model.Flight;
import com.ats.simplifly.model.Route;
import com.ats.simplifly.model.User;
import com.ats.simplifly.repository.FlightRepository;
import org.springframework.stereotype.Service;

@Service
public class FlightService {


    private final FlightRepository flightRepository;

    public FlightService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    public Flight addFlight(Flight flight){
       return flightRepository.save(flight);
    }

}
