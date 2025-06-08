package com.ats.simplifly.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ats.simplifly.exception.ResourceNotFoundException;
import com.ats.simplifly.model.Flight;
import com.ats.simplifly.model.Schedule;
import com.ats.simplifly.repository.FlightRepository;
import com.ats.simplifly.repository.ScheduleRepository;

@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final FlightRepository flightRepository;
    private final SeatService seatService;

    public ScheduleService(ScheduleRepository scheduleRepository, FlightRepository flightRepository, SeatService seatService) {
        this.scheduleRepository = scheduleRepository;
        this.flightRepository = flightRepository;
        this.seatService = seatService;
    }

    /*
       To add schedule:
       1. Check whether flight exists
       2. If it exists take owner and flight id and set it to schedule
       3. Else Send a message of adding flight
       4. Add schedule
     */
    public Schedule scheduleFlight(Schedule schedule){
        Flight flight = schedule.getFlight();
        Flight checkIfFlightExists = flightRepository.findByFlightNumber(flight.getFlightNumber());
        if(checkIfFlightExists != null){
            flight.setId(checkIfFlightExists.getId());
            flight.setOwner(checkIfFlightExists.getOwner());
        }
        else{
            throw new ResourceNotFoundException("You must add the flight before scheduling it");
        }
        schedule.setFlight(flight);
        Schedule finalSchedule = scheduleRepository.save(schedule);
        seatService.createSeats(schedule);
        return finalSchedule;
    }

    //Updating schedule
    public Schedule updateSchedule(Schedule schedule, int scheduleId){
        Schedule scheduleToUpdate = scheduleRepository.findById(scheduleId).orElseThrow(()->new ResourceNotFoundException("Schedule not found to update"));

        if(schedule.getArrivalTime()!=null){
            scheduleToUpdate.setArrivalTime(schedule.getArrivalTime());
        }
        if(schedule.getFare()!=0){
            scheduleToUpdate.setFare(schedule.getFare());
        }
        if(schedule.getFlight()!=null){
            scheduleToUpdate.setFlight(schedule.getFlight());
        }
        if(schedule.getDepartureTime()!=null){
            scheduleToUpdate.setDepartureTime(schedule.getDepartureTime());
        }
        return scheduleRepository.save(scheduleToUpdate);
    }
    //Getting schedule by Id
    public Schedule getScheduleByFlight(int flightId)
    {
        return scheduleRepository.getByFlight(flightId);
    }

    //The user will see the schedules that he has created not all the schedules in the database
    public List<Schedule> getAllSchedules(String username){

        List<Schedule> allSchedules = scheduleRepository.findByOwner(username);

        return allSchedules;
    }

    public void deleteSchedule(int scheduleId){
        if(scheduleRepository.findById(scheduleId) == null){
            throw new ResourceNotFoundException("Schedule Not found");
        }
        seatService.deleteSeats(scheduleId);
        scheduleRepository.deleteById(scheduleId);
    }


    public List<Flight> getFlightsByFareAndRoute(String origin, String destination, double fare) {
        return scheduleRepository.getFlightsByFareAndRoute(origin, destination, fare);
    }

    public List<Schedule> getFlightSearch(String origin, String destination, LocalDateTime date) {

        return scheduleRepository.searchFlight(origin, destination, date);
    }
}
