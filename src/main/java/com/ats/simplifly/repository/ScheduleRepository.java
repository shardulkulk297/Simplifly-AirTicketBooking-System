package com.ats.simplifly.repository;

import com.ats.simplifly.model.Flight;
import com.ats.simplifly.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {

    @Query("Select s from Schedule s WHERE s.flight.owner.user.username = ?1")
    Schedule getByUsername(String username);

    @Query("Select s from Schedule s WHERE s.flight.id = ?1")
    Schedule getByFlight(int flightId);

    @Query("Select s from Schedule s WHERE s.flight.owner.user.username = ?1")
    List<Schedule> findByOwner(String username);

    @Query("Select s from Schedule s WHERE s.flight.route.origin = ?1 AND s.flight.route.destination = ?2 AND s.fare = ?3")
    List<Flight> getFlightsByFareAndRoute(String origin, String destination, double fare);
}
