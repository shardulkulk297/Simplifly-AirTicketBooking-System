package com.ats.simplifly.repository;

import com.ats.simplifly.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface FlightRepository extends JpaRepository<Flight, Integer> {

    @Query("Select f from Flight f WHERE f.owner.id = ?1")
    List<Flight> getAllFlights(int id);

    @Query("Select f from Flight f WHERE f.flightNumber = ?1")
    Flight findByFlightNumber(String flightNumber);

    @Query("Select f from Flight f WHERE f.route.origin = ?1 AND f.route.destination = ?2")
    List<Flight> getFlightsByRoute(String origin, String destination);

    @Query("Select count(f.id) from Flight f")
    int getTotalFlights();
}
