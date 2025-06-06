package com.ats.simplifly.model;

import com.ats.simplifly.model.enums.SeatClassType;
import com.ats.simplifly.model.enums.SeatStatus;
import jakarta.persistence.*;

@Entity
@Table(name = "seat")
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String seatNumber;
    @ManyToOne
    private Schedule schedule;
    @Column(name = "class_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private SeatClassType seatClassType;
    @Enumerated(EnumType.STRING)
    private SeatStatus seatStatus;
    private double price;

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
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
}
