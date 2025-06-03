package com.ats.simplifly.repository;

import com.ats.simplifly.model.PlatformManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ManagerRepository extends JpaRepository<PlatformManager, Integer> {
    @Query("Select p from PlatformManager p WHERE p.user.username = ?1")
    PlatformManager getByUsername(String username);
}
