package com.ats.simplifly.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.ats.simplifly.dto.FlightDto;
import com.ats.simplifly.dto.FlightOwnerDto;
import com.ats.simplifly.dto.ScheduleDto;
import com.ats.simplifly.dto.UserDto;
import com.ats.simplifly.model.FlightOwner;
import com.ats.simplifly.model.Route;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public Schedule updateSchedule(Schedule schedule, int scheduleId) {
        Schedule scheduleToUpdate = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ResourceNotFoundException("Schedule not found to update"));

        if (schedule.getArrivalTime() != null) {
            scheduleToUpdate.setArrivalTime(schedule.getArrivalTime());
        }
        if (schedule.getDepartureTime() != null) {
            scheduleToUpdate.setDepartureTime(schedule.getDepartureTime());
        }
        if (schedule.getFare() != 0) {
            scheduleToUpdate.setFare(schedule.getFare());
        }
        if (schedule.getFlight() != null) {
            scheduleToUpdate.setFlight(schedule.getFlight());
        }
        if (schedule.getIsWifiAvailable() != null) {
            scheduleToUpdate.setIsWifiAvailable(schedule.getIsWifiAvailable());
        }
        if (schedule.getFreeMeal() != null) {
            scheduleToUpdate.setFreeMeal(schedule.getFreeMeal());
        }
        if (schedule.getMealAvailable() != null) {
            scheduleToUpdate.setMealAvailable(schedule.getMealAvailable());
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
        Schedule existing = scheduleRepository.findById(scheduleId).orElseThrow(()->new ResourceNotFoundException("Schedule Not found"));
        seatService.deleteSeats(existing.getId());
        scheduleRepository.delete(existing);
    }

    public List<Flight> getFlightsByFareAndRoute(String origin, String destination, double fare) {
        return scheduleRepository.getFlightsByFareAndRoute(origin, destination, fare);
    }

    public List<ScheduleDto> getFlightSearch(String origin, String destination, String datee, Integer page,Integer size) {
        LocalDate date = LocalDate.parse(datee);
        LocalDateTime now = LocalDateTime.now();

        LocalDateTime start = date.isEqual(LocalDate.now()) ? now : date.atStartOfDay();
        LocalDateTime end = date.plusDays(1).atStartOfDay();

        Pageable pageable = PageRequest.of(page, size);
        Page<Schedule> schedules = scheduleRepository.searchFlight(origin, destination, start, end, pageable);
        List<Schedule> scheduleList = schedules.getContent();

        List<ScheduleDto> scheduleDtos = new ArrayList<>();
        for (Schedule schedule : scheduleList) {
            scheduleDtos.add(convertToScheduleDto(schedule));
        }

        return scheduleDtos;
    }

    public ScheduleDto getSchedule(int scheduleId) {
        if (scheduleId <= 0) {
            throw new RuntimeException("Incorrect ScheduleId");
        }

        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ResourceNotFoundException("Schedule Not Found"));

        return convertToScheduleDto(schedule);
    }

    public ScheduleDto convertToScheduleDto(Schedule schedule) {
        ScheduleDto dto = new ScheduleDto();
        dto.setId(schedule.getId());
        dto.setDepartureTime(schedule.getDepartureTime());
        dto.setArrivalTime(schedule.getArrivalTime());
        dto.setFare(schedule.getFare());
        dto.setIsWifiAvailable(schedule.getIsWifiAvailable());
        dto.setFreeMeal(schedule.getFreeMeal());
        dto.setMealAvailable(schedule.getMealAvailable());
        dto.setBusinessClassRate(schedule.getBusinessClassRate());
        dto.setFirstClassRate(schedule.getFirstClassRate());

        // Flight
        Flight flight = schedule.getFlight();
        FlightDto flightDto = new FlightDto();
        flightDto.setId(flight.getId());
        flightDto.setFlightNumber(flight.getFlightNumber());
        flightDto.setBaggageCheckin(flight.getBaggageCheckin());
        flightDto.setBaggageCabin(flight.getBaggageCabin());
        flightDto.setTotalSeats(flight.getTotalSeats());
        flightDto.setFirstClassSeats(flight.getFirstClassSeats());
        flightDto.setBusinessClassSeats(flight.getBusinessClassSeats());

        // Flight Owner
        FlightOwner owner = flight.getOwner();
        FlightOwnerDto ownerDto = new FlightOwnerDto();
        ownerDto.setId(owner.getId());
        ownerDto.setCompanyName(owner.getCompanyName());
        ownerDto.setEmail(owner.getEmail());
        ownerDto.setContactPhone(owner.getContactPhone());
        ownerDto.setVerificationStatus(owner.getVerificationStatus());
        ownerDto.setLogoLink(owner.getLogoLink());

        UserDto userDto = new UserDto();
        userDto.setId(owner.getUser().getId());
        userDto.setUsername(owner.getUser().getUsername());
        userDto.setRole(owner.getUser().getRole());

        ownerDto.setUser(userDto);
        flightDto.setOwner(ownerDto);

        // Route
        Route route = flight.getRoute();


        flightDto.setRoute(route);
        dto.setFlight(flightDto);

        return dto;
    }

}
