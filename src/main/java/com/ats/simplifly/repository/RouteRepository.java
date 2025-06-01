package com.ats.simplifly.repository;

import com.ats.simplifly.model.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RouteRepository extends JpaRepository<Route, Integer> {

    @Query("Select r from Route r WHERE r.origin = ?1 AND r.destination=?2")
    Route findByRoute(String origin, String destination);
}
