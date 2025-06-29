package com.ats.simplifly.repository;

import com.ats.simplifly.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {
    @Query("Select b from Booking b WHERE b.customer.user.username=?1")
    List<Booking> getByCustomer(String name);
}
