package com.ats.simplifly.service;

import com.ats.simplifly.dto.*;
import com.ats.simplifly.model.*;
import org.springframework.stereotype.Service;

import com.ats.simplifly.model.enums.SeatClassType;
import com.ats.simplifly.model.enums.SeatStatus;
import com.ats.simplifly.repository.SeatRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class SeatService {


    private SeatRepository seatRepository;

    public SeatService(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    public Seat createSeat(Seat seat) {
        return seatRepository.save(seat);
    }

    /*
    Entry point from where creation of seats will start
     */
    public void createSeats(Schedule schedule){

        Flight flight = schedule.getFlight();
        /*
        First class seats will be created first
         */
        createFirstClassSeats(schedule, flight);
        createBusinessClassSeats(schedule, flight);
        createEconomyClassSeats(schedule, flight);
    }
    /*
    Helper method for creating first class seats
     */
    public void createFirstClassSeats(Schedule schedule, Flight flight) {
        /*
        First class seats will map 2 seats per row as most planes have 2 first class seats per row
         */
        int firstClassRows = (int) Math.ceil(flight.getFirstClassSeats() / 2.0); //2 Seats Per Row
        if(flight.getFirstClassSeats()==0){
            return;
        }
        /*
        As there will be two seats per row the letters for that will be A & B So it will be like
        1A, 1B
         */
        /*
        This loop will iterate till no of first class seats and no of first class rows which is 2
         */
        int seatCount = 0;
        for(int row=1; row<=firstClassRows && seatCount < flight.getFirstClassSeats(); row++){
            for(char seatLetter = 'A'; seatLetter<='B'; seatLetter++){
                Seat seat = new Seat();
                seat.setSeatStatus(SeatStatus.AVAILABLE);
                seat.setSeatNumber(row + String.valueOf(seatLetter));
                seat.setSchedule(schedule);
                seat.setSeatClassType(SeatClassType.FIRST);
                double firstclassRate = schedule.getFirstClassRate()!=null ? schedule.getFirstClassRate(): 3;
                seat.setPrice(schedule.getFare() * firstclassRate);
                createSeat(seat);
                seatCount++;
            }
        }

    }
    /*
    Helper method for creating biz seats
     */
    private void createBusinessClassSeats(Schedule schedule, Flight flight) {
        /*
        First the total first class rows are calculated as we are coming from top to bottom so we need to see where the biz class rows will start
         */
        int firstClassRows = (int) Math.ceil(flight.getFirstClassSeats() / 2.0);
        int bizRows = (int) Math.ceil(flight.getBusinessClassSeats() / 4.0); //4 seats per row

        /*
        If there are no business class seats then we will just return
         */
        if(flight.getBusinessClassSeats() == 0){
            return;
        }
        /*
        the row creation will start after the no. of first class rows
         */
        int start = firstClassRows + 1;
        /*
        Determining the end
         */
        int end = start + bizRows - 1;
        int seatCount = 0;
        /*
        4 Seats per row so A,B,C,D
         */
        for(int row = start; row<=end && seatCount<flight.getBusinessClassSeats(); row++){
            for(char seatLetter = 'A'; seatLetter<='D'; seatLetter++){
                Seat seat = new Seat();
                seat.setSeatStatus(SeatStatus.AVAILABLE);
                seat.setSeatNumber(row + String.valueOf(seatLetter));
                seat.setSchedule(schedule);
                seat.setSeatClassType(SeatClassType.BUSINESS);
                double businessClassRate = schedule.getBusinessClassRate()!=null ? schedule.getBusinessClassRate():1.5;
                seat.setPrice(schedule.getFare() * businessClassRate);
                createSeat(seat);
                seatCount++;
            }
        }

    }
    /*
    Helper method for creating economy seats
     */
    private void createEconomyClassSeats(Schedule schedule, Flight flight) {

        /*
        Calculating total eco seats
         */
        int totalSeats = flight.getTotalSeats();
        int firstClassSeats = flight.getFirstClassSeats();
        int businessClassSeats = flight.getBusinessClassSeats();
        int economySeats = totalSeats - (firstClassSeats + businessClassSeats);

        /*
        Calculating start of the row
         */
        int firstClassRows = (int) Math.ceil(flight.getFirstClassSeats() / 2.0);
        int bizRows = (int) Math.ceil(flight.getBusinessClassSeats() / 4.0);
        /*
        6 seats per row
         */
        int economyRows = (int) Math.ceil(economySeats / 6.0);

        if(economySeats <= 0 ){
            return;
        }

        int start = firstClassRows + bizRows + 1;
        int end = start + economyRows;

        /*
        6 seats so letters: A,B,C,D,E,F
         */
        int seatCount = 0;
        for(int row = start; row<end && seatCount<economySeats; row++){
            for(char seatLetter = 'A'; seatLetter<='F'; seatLetter++){
                Seat seat = new Seat();
                seat.setSeatStatus(SeatStatus.AVAILABLE);
                seat.setSeatNumber(row + String.valueOf(seatLetter));
                seat.setSchedule(schedule);
                seat.setSeatClassType(SeatClassType.ECONOMY);
                seat.setPrice(schedule.getFare());
                createSeat(seat);
                seatCount++;
            }
        }
    }

    public void deleteSeats(int scheduleId) {
        seatRepository.deleteSeatsBySchedule(scheduleId);
    }

    public double calculateTotalPrice(Schedule schedule, List<String> seatNumbers) {
        double totalAmount = 0;

        for(String seatNumber: seatNumbers)
        {
            double price = seatRepository.getSeatPrice(schedule.getId(),seatNumber);
            totalAmount = totalAmount + price;
        }
        return totalAmount;

    }

    public void holdSeats(Schedule schedule, List<String> seatNumbers) {

        for(String seatNumber: seatNumbers){
            Seat seat = seatRepository.getBySeatNumber(seatNumber, schedule.getId());
            seat.setSeatStatus(SeatStatus.HOLD);
            seatRepository.save(seat);
        }

    }

    public boolean checkAvailableTickets(Schedule schedule, int noOfTickets) {
        int tickets = seatRepository.checkAvailableTickets(schedule.getId(), SeatStatus.AVAILABLE);
        if(tickets >= noOfTickets){
            return true;
        }
        return false;
    }

    public void flipStatus(Schedule schedule) {
        List<Seat> seats = seatRepository.getBySchedule(schedule.getId());

        for(Seat seat: seats){
            seat.setSeatStatus(SeatStatus.AVAILABLE);
            seatRepository.save(seat);
        }

    }

    public void confirmSeats(List<String> seatNumbers, Schedule schedule) {
        for(String seatNumber: seatNumbers){
            Seat seat = seatRepository.getBySeatNumber(seatNumber, schedule.getId());
            seat.setSeatStatus(SeatStatus.BOOKED);
            seatRepository.save(seat);
        }
    }

    public List<SeatDto> getSeatsBySchedule(int scheduleId) {
        List<Seat> seats = seatRepository.getSeatsBySchedule(scheduleId);
        List<SeatDto> seatDtos = new ArrayList<>();

        for (Seat seat : seats) {
            SeatDto dto = new SeatDto();
            dto.setId(seat.getId());
            dto.setSeatNumber(seat.getSeatNumber());
            dto.setSeatClassType(seat.getSeatClassType());
            dto.setSeatStatus(seat.getSeatStatus());
            dto.setPrice(seat.getPrice());
            dto.setSchedule(convertToScheduleDto(seat.getSchedule()));
            seatDtos.add(dto);
        }
        return seatDtos;
    }

    public void makeSeatsAvailable(List<String> seatNumbers, Schedule schedule) {
        seatRepository.makeSeatsAvailable(seatNumbers, schedule.getId());
    }

    private ScheduleDto convertToScheduleDto(Schedule schedule) {
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
