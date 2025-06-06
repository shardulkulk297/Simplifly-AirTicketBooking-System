package com.ats.simplifly.service;

import com.ats.simplifly.model.Booking;
import com.ats.simplifly.model.Flight;
import com.ats.simplifly.model.Schedule;
import com.ats.simplifly.model.Seat;
import com.ats.simplifly.model.enums.SeatClassType;
import com.ats.simplifly.model.enums.SeatStatus;
import com.ats.simplifly.repository.SeatRepository;
import org.springframework.stereotype.Service;

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


    public void createSeats(Schedule schedule){

        Flight flight = schedule.getFlight();

        int firstClassRows = flight.getFirstClassRows();
        int buisnessClassRows = flight.getBusinessClassRows();

        createFirstClassSeats(schedule, flight);
        createBusinessClassSeats(schedule, flight);
        createEconomyClassSeats(schedule, flight);



    }

    private void createFirstClassSeats(Schedule schedule, Flight flight) {
        if(flight.getFirstClassRows()==0){
            return;
        }
        for(int row=1; row<=flight.getFirstClassRows(); row++){
            for(char seatLetter = 'A'; seatLetter<='B'; seatLetter++){
                Seat seat = new Seat();
                seat.setSeatStatus(SeatStatus.AVAILABLE);
                seat.setSeatNumber(row + String.valueOf(seatLetter));
                seat.setSchedule(schedule);
                seat.setSeatClassType(SeatClassType.FIRST);
                seat.setPrice(schedule.getFare()*5);
                createSeat(seat);
            }
        }

    }

    private void createBusinessClassSeats(Schedule schedule, Flight flight) {

        if(flight.getBusinessClassRows() == 0){
            return;
        }

        int start = flight.getFirstClassRows() + 1;
        int end = start + flight.getBusinessClassRows() - 1;

        for(int row = start; row<=end; row++){
            for(char seatLetter = 'A'; seatLetter<='D'; seatLetter++){
                Seat seat = new Seat();
                seat.setSeatStatus(SeatStatus.AVAILABLE);
                seat.setSeatNumber(row + String.valueOf(seatLetter));
                seat.setSchedule(schedule);
                seat.setSeatClassType(SeatClassType.BUSINESS);
                seat.setPrice(schedule.getFare() * 3);
                createSeat(seat);
            }
        }

    }

    private void createEconomyClassSeats(Schedule schedule, Flight flight) {


        int totalSeats = flight.getTotalSeats();
        int firstClassSeats = flight.getFirstClassRows() * 2;
        int businessClassSeats = flight.getBusinessClassRows() * 4;
        int economySeats = totalSeats - (firstClassSeats + businessClassSeats);

        if(economySeats <= 0 ){
            return;
        }


        int start = flight.getFirstClassRows() + flight.getBusinessClassRows();
        int economyRows = (int) Math.ceil(economySeats / 6.0);

        for(int row = start; row<start + economyRows; row++){
            for(char seatLetter = 'A'; seatLetter<='F'; seatLetter++){
                Seat seat = new Seat();
                seat.setSeatStatus(SeatStatus.AVAILABLE);
                seat.setSeatNumber(row + String.valueOf(seatLetter));
                seat.setSchedule(schedule);
                seat.setSeatClassType(SeatClassType.ECONOMY);
                seat.setPrice(schedule.getFare());
                createSeat(seat);
            }
        }
    }

    public void deleteSeats(int scheduleId) {
        seatRepository.deleteSeatsBySchedule(scheduleId);
    }
}
