package com.ats.simplifly.service;

import com.ats.simplifly.exception.ResourceNotFoundException;
import com.ats.simplifly.model.Flight;
import com.ats.simplifly.model.FlightOwner;
import com.ats.simplifly.model.Route;
import com.ats.simplifly.model.User;
import com.ats.simplifly.repository.FlightOwnerRepository;
import com.ats.simplifly.repository.FlightRepository;
import com.ats.simplifly.repository.RouteRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlightService {


    private final FlightRepository flightRepository;
    private final FlightOwnerRepository flightOwnerRepository;
    private final RouteService routeService;
    private final RouteRepository routeRepository;

    public FlightService(FlightRepository flightRepository, FlightOwnerRepository flightOwnerRepository, RouteService routeService, RouteRepository routeRepository) {
        this.flightRepository = flightRepository;
        this.flightOwnerRepository = flightOwnerRepository;
        this.routeService = routeService;
        this.routeRepository = routeRepository;
    }

    /*
    To addFlight:
    1. First Take owner by username and set it to flight
    2. Get the route and check whether it exists or not.
    3. If the route exists then take it and set it to the route.
    4. If not add the route to DB.
    5. Set the route to the flight
    6. Save the flight
     */

    public Flight addFlight(Flight flight, String username){
        FlightOwner flightOwner = flightOwnerRepository.getByUsername(username);
        flight.setOwner(flightOwner);
        Route route = flight.getRoute();
        Route mainRoute = routeRepository.findByRoute(route.getOrigin(), route.getDestination());
        if(mainRoute!=null)
        {
            flight.setRoute(mainRoute);
        }
        else{
            route = routeRepository.save(route);
            flight.setRoute(route);
        }

        return flightRepository.save(flight);
    }

    /*
    WIll return the only flights that are created by logged in flight owner
     */

    public List<Flight> getAllFlights(String username) {
        FlightOwner flightOwner = flightOwnerRepository.getByUsername(username);
        int id = flightOwner.getId();
        return flightRepository.getAllFlights(id);
    }


    public Flight getFlight(int id){
        Flight flight =  flightRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Flight Not found"));
        return flight;
    }

    //Update the flight using id

    public Flight updateFlight(Flight flight, int id){

        Flight flightToUpdate = flightRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Flight Not found to update"));

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


    public List<Flight> getAllFlightsForSearch() {
        return flightRepository.findAll();
    }

    public List<Flight> getFlightsByRoute(String origin, String destination) {
        return flightRepository.getFlightsByRoute(origin, destination);
    }

    public int getTotalFlights() {
        return flightRepository.getTotalFlights();
    }
}
