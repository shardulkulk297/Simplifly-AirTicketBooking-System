package com.ats.simplifly.repository;

import com.ats.simplifly.model.Customer;
import com.ats.simplifly.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    @Query("SELECT c from Customer c WHERE c.user.username = ?1")
    Customer getByUsername(String username);

    String user(User user);
}
