package com.ats.simplifly.repository;

import com.ats.simplifly.model.BookingSeat;
import com.ats.simplifly.model.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingSeatRepository extends JpaRepository<BookingSeat, Integer> {
    @Query("Select p.passenger from BookingSeat p WHERE p.booking.id = ?1")
    List<Passenger> getPassengersByBooking(int bookingId);
    @Query("Select s.seat.seatNumber from BookingSeat s WHERE s.booking.id=?1")
    List<String> getSeats(int id);
}
