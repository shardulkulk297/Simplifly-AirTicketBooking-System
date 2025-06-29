package com.ats.simplifly.repository;

import com.ats.simplifly.model.Passenger;
import com.ats.simplifly.model.enums.Gender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Integer> {
    @Query("Select p from Passenger p WHERE p.customer.user.username =?1 AND p.name = ?2 AND p.gender = ?3 AND p.age = ?4")
    Passenger findByCustomerNameGenderAge(String username, String name, Gender gender, int age);
}
