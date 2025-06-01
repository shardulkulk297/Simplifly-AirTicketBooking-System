package com.ats.simplifly.repository;

import com.ats.simplifly.model.FlightOwner;
import com.ats.simplifly.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightOwnerRepository extends JpaRepository<FlightOwner, Integer> {

    @Query("Select f from FlightOwner f WHERE f.user.username = ?1")
    FlightOwner getByUsername(String username);

    String user(User user);
}
