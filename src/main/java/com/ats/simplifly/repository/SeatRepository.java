package com.ats.simplifly.repository;

import com.ats.simplifly.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Integer> {
    @Query("Delete FROM Seat s WHERE s.schedule.id = ?1")
    void deleteSeatsBySchedule(int scheduleId);
}
