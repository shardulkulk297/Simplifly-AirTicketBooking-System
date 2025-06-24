package com.ats.simplifly.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ats.simplifly.model.Flight;
import com.ats.simplifly.model.Schedule;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {

    @Query("Select s from Schedule s WHERE s.flight.owner.user.username = ?1 AND s.status = 'ACTIVE'")
    Schedule getByUsername(String username);

    @Query("Select s from Schedule s WHERE s.flight.id = ?1")
    Schedule getByFlight(int flightId);

    @Query("Select s from Schedule s WHERE s.flight.owner.user.username = ?1 AND s.status = 'ACTIVE'")
    List<Schedule> findByOwner(String username);

    @Query("Select s from Schedule s WHERE s.flight.route.origin = ?1 AND s.flight.route.destination = ?2 AND s.fare = ?3 AND s.status = 'ACTIVE'")
    List<Flight> getFlightsByFareAndRoute(String origin, String destination, double fare);

    @Query("Select s from Schedule s WHERE s.flight.route.origin = ?1 AND s.flight.route.destination = ?2 AND s.departureTime BETWEEN ?3 AND ?4 AND s.status = 'ACTIVE'")
    List<Schedule> searchFlight(String origin, String destination, LocalDateTime start, LocalDateTime end);
}
