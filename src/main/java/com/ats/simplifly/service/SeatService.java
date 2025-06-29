package com.ats.simplifly.service;

import org.springframework.stereotype.Service;

import com.ats.simplifly.model.Flight;
import com.ats.simplifly.model.Schedule;
import com.ats.simplifly.model.Seat;
import com.ats.simplifly.model.enums.SeatClassType;
import com.ats.simplifly.model.enums.SeatStatus;
import com.ats.simplifly.repository.SeatRepository;

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


    public void createSeats(Schedule schedule){

        Flight flight = schedule.getFlight();

        createFirstClassSeats(schedule, flight);
        createBusinessClassSeats(schedule, flight);
        createEconomyClassSeats(schedule, flight);
    }

    private void createFirstClassSeats(Schedule schedule, Flight flight) {
        
        int firstClassRows = (int) Math.ceil(flight.getFirstClassSeats() / 2.0); //2 Seats Per Row
        if(flight.getFirstClassSeats()==0){
            return;
        }
        
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

    private void createBusinessClassSeats(Schedule schedule, Flight flight) {

        int firstClassRows = (int) Math.ceil(flight.getFirstClassSeats() / 2.0);
        int bizRows = (int) Math.ceil(flight.getBusinessClassSeats() / 4.0); //4 seats per row


        if(flight.getBusinessClassSeats() == 0){
            return;
        }

        

        int start = firstClassRows + 1;
        int end = start + bizRows - 1;
        int seatCount = 0;
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

    private void createEconomyClassSeats(Schedule schedule, Flight flight) {


        int totalSeats = flight.getTotalSeats();
        int firstClassSeats = flight.getFirstClassSeats();
        int businessClassSeats = flight.getBusinessClassSeats();
        int economySeats = totalSeats - (firstClassSeats + businessClassSeats);

        int firstClassRows = (int) Math.ceil(flight.getFirstClassSeats() / 2.0);
        int bizRows = (int) Math.ceil(flight.getBusinessClassSeats() / 4.0);
        int economyRows = (int) Math.ceil(economySeats / 6.0);

        if(economySeats <= 0 ){
            return;
        }

        int start = firstClassRows + bizRows + 1;
        int end = start + economyRows;
       
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

    public List<Seat> getSeatsBySchedule(int scheduleId) {
        return seatRepository.getSeatsBySchedule(scheduleId);
    }
}
