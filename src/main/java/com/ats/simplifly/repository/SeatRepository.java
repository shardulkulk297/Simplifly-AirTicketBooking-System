package com.ats.simplifly.repository;

import com.ats.simplifly.model.Schedule;
import com.ats.simplifly.model.Seat;
import com.ats.simplifly.model.enums.SeatStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Integer> {

    @Modifying
    @Transactional
    @Query("Delete FROM Seat s WHERE s.schedule.id = ?1")
    void deleteSeatsBySchedule(int scheduleId);

    @Query("Select s from Seat s WHERE s.schedule.id = ?1")
    List<Seat> getBySchedule(int id);

    @Query("Select count(s.schedule.id) from Seat s WHERE s.schedule.id = ?1 AND s.seatStatus = ?2")
    int checkAvailableTickets(int scheduleId, SeatStatus seatStatus);

    @Query("Select s.price from Seat s WHERE s.schedule.id = ?1 AND s.seatNumber = ?2")
    double getSeatPrice(int scheduleId, String seatNumber);


    @Query("Select s from Seat s WHERE s.seatNumber = ?1 AND s.schedule.id = ?2")
    Seat getBySeatNumber(String seatNumber, int scheduleId);

    @Query("Select s from Seat s WHERE s.schedule.id = ?1")
    List<Seat> getSeatsBySchedule(int scheduleId);
}
