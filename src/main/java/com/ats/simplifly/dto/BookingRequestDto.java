package com.ats.simplifly.dto;

import com.ats.simplifly.model.Passenger;
import com.ats.simplifly.model.Schedule;
import com.ats.simplifly.model.Seat;
import com.ats.simplifly.model.enums.PaymentMethod;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookingRequestDto {

    private int scheduleId;
    private List<Passenger> passengers;
    private List<String> seatNumbers;
    private int noOfTickets;
    private PaymentMethod paymentMethod;

    public List<String> getSeatNumbers() {
        return seatNumbers;
    }

    public void setSeatNumbers(List<String> seatNumbers) {
        this.seatNumbers = seatNumbers;
    }

    public int getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }

    public List<Passenger> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<Passenger> passengers) {
        this.passengers = passengers;
    }

    public int getNoOfTickets() {
        return noOfTickets;
    }

    public void setNoOfTickets(int noOfTickets) {
        this.noOfTickets = noOfTickets;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
