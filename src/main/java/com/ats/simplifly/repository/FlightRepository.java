package com.ats.simplifly.repository;

import com.ats.simplifly.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FlightRepository extends JpaRepository<Flight, Integer> {

    @Query("Select f from Flight f WHERE f.owner.id = ?1")
    List<Flight> getAllFlights(int id);

    @Query("Select f from Flight f WHERE f.flightNumber = ?1")
    Flight findByFlightNumber(String flightNumber);

}
