package com.ats.simplifly.service;

import com.ats.simplifly.exception.ResourceNotFoundException;
import com.ats.simplifly.exception.SeatsNotAvailableException;
import com.ats.simplifly.model.Customer;
import com.ats.simplifly.model.Passenger;
import com.ats.simplifly.model.Schedule;
import com.ats.simplifly.model.Seat;
import com.ats.simplifly.model.enums.SeatStatus;
import com.ats.simplifly.repository.CustomerRepository;
import com.ats.simplifly.repository.PassengerRepository;
import com.ats.simplifly.repository.SeatRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PassengerService {


    private final CustomerRepository customerRepository;
    private final PassengerRepository passengerRepository;
    private final SeatRepository seatRepository;

    public PassengerService(CustomerRepository customerRepository, PassengerRepository passengerRepository, SeatRepository seatRepository) {

        this.customerRepository = customerRepository;
        this.passengerRepository = passengerRepository;
        this.seatRepository = seatRepository;
    }

    public Passenger addPassenger(Passenger passenger, String username){
        Customer customer = customerRepository.getByUsername(username);
        passenger.setCustomer(customer);
        Passenger passengerr = passengerRepository.findByCustomerNameGenderAge(customer.getUser().getUsername(), passenger.getName(), passenger.getGender(), passenger.getAge());
        if(passengerr!=null){
            return passengerr;
        }
        return passengerRepository.save(passenger);
    }



    public Map<Passenger, Seat> mapPassengersToSeats(Schedule schedule, List<Passenger> passengers, List<String> seatNumbers) {

        if(passengers.size()!=seatNumbers.size()){
            throw new RuntimeException("Equal Seats should be booked");
        }


        List<Seat> seats = seatRepository.getBySchedule(schedule.getId());
        Map<String, Seat> seatBySeatNumber = new HashMap<>();
        for(Seat seat: seats){
            seatBySeatNumber.put(seat.getSeatNumber(), seat);
        }
        Map<Passenger, Seat> mapPassengersToSeat = new HashMap<>();
        for(int i=0; i<passengers.size(); i++){
            Passenger p = passengers.get(i);
            String seatNumber = seatNumbers.get(i);
            Seat seat = seatBySeatNumber.get(seatNumber);

            if (seat == null) {
                throw new ResourceNotFoundException("Seat " + seatNumber + " not found for schedule " + schedule.getId());
            }
            if (seat.getSeatStatus() != SeatStatus.HOLD) {
                throw new SeatsNotAvailableException("Seat " + seatNumber + " is not available");
            }

            mapPassengersToSeat.put(p, seat);
        }

        return mapPassengersToSeat;

    }
}
