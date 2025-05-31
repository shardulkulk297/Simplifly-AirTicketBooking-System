package com.ats.simplifly.service;

import com.ats.simplifly.exception.ResourceNotFoundException;
import com.ats.simplifly.model.Flight;
import com.ats.simplifly.model.FlightOwner;
import com.ats.simplifly.model.Route;
import com.ats.simplifly.model.User;
import com.ats.simplifly.repository.FlightOwnerRepository;
import com.ats.simplifly.repository.FlightRepository;
import com.ats.simplifly.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlightService {


    private final FlightRepository flightRepository;
    private final FlightOwnerRepository flightOwnerRepository;
    private final RouteService routeService;

    public FlightService(FlightRepository flightRepository, FlightOwnerRepository flightOwnerRepository, RouteService routeService) {
        this.flightRepository = flightRepository;
        this.flightOwnerRepository = flightOwnerRepository;
        this.routeService = routeService;
    }

    public Flight addFlight(Flight flight){

        return flightRepository.save(flight);
    }

    public List<Flight> getAllFlights(String username) {
        FlightOwner flightOwner = flightOwnerRepository.getByUsername(username);
        int id = flightOwner.getId();
        return flightRepository.getAllFlights(id);
    }

    public Flight getFlight(int id){
        Flight flight =  flightRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Flight Not found"));
        return flight;
    }

    public Flight updateFlight(Flight flight, String username){

        Flight flightToUpdate = flightRepository.getByOwner(username);

        if(flight.getRoute()!=null){
            Route route = routeService.updateRoute(flight.getRoute(), flight.getRoute().getId());
            flightToUpdate.setRoute(route);
        }
        if(flight.getFlightNumber()!=null)
        {
            flightToUpdate.setFlightNumber(flight.getFlightNumber());
        }
        if(flight.getBaggageCabin()!=0)
        {
            flightToUpdate.setBaggageCabin(flight.getBaggageCabin());
        }
        if(flight.getBaggageCheckin()!=0){
            flightToUpdate.setBaggageCheckin(flight.getBaggageCheckin());
        }
        if(flight.getTotalSeats()!=0){
            flightToUpdate.setTotalSeats(flight.getTotalSeats());
        }

        return flightRepository.save(flightToUpdate);
    }

    public void deleteFlight(int id){
        flightRepository.deleteById(id);
    }




}
