package com.ats.simplifly.service;

import com.ats.simplifly.model.Customer;
import com.ats.simplifly.model.Passenger;
import com.ats.simplifly.repository.CustomerRepository;
import com.ats.simplifly.repository.PassengerRepository;
import org.springframework.stereotype.Service;

@Service
public class PassengerService {


    private final CustomerRepository customerRepository;
    private final PassengerRepository passengerRepository;

    public PassengerService(CustomerRepository customerRepository, PassengerRepository passengerRepository) {

        this.customerRepository = customerRepository;
        this.passengerRepository = passengerRepository;
    }

    public Passenger addPassenger(Passenger passenger, String username){
        Customer customer = customerRepository.getByUsername(username);
        Customer main = passenger.getCustomer();
        main.setId(customer.getId());
        passenger.setCustomer(main);
        return passengerRepository.save(passenger);
    }



}
