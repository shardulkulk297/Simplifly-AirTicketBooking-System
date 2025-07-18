package com.ats.simplifly.dto;

import com.ats.simplifly.model.enums.SeatClassType;
import com.ats.simplifly.model.enums.SeatStatus;

import java.util.Objects;

public class SeatDto {
    private int id;
    private String seatNumber;
    private SeatClassType seatClassType;
    private SeatStatus seatStatus;
    private double price;
    private ScheduleDto schedule;

    public SeatDto() {}

    public SeatDto(int id, String seatNumber, SeatClassType seatClassType,
                   SeatStatus seatStatus, double price, ScheduleDto schedule) {
        this.id = id;
        this.seatNumber = seatNumber;
        this.seatClassType = seatClassType;
        this.seatStatus = seatStatus;
        this.price = price;
        this.schedule = schedule;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public SeatClassType getSeatClassType() {
        return seatClassType;
    }

    public void setSeatClassType(SeatClassType seatClassType) {
        this.seatClassType = seatClassType;
    }

    public SeatStatus getSeatStatus() {
        return seatStatus;
    }

    public void setSeatStatus(SeatStatus seatStatus) {
        this.seatStatus = seatStatus;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public ScheduleDto getSchedule() {
        return schedule;
    }

    public void setSchedule(ScheduleDto schedule) {
        this.schedule = schedule;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof SeatDto seatDto)) return false;
        return id == seatDto.id && Double.compare(price, seatDto.price) == 0 && Objects.equals(seatNumber, seatDto.seatNumber) && seatClassType == seatDto.seatClassType && seatStatus == seatDto.seatStatus && Objects.equals(schedule, seatDto.schedule);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, seatNumber, seatClassType, seatStatus, price, schedule);
    }
}
