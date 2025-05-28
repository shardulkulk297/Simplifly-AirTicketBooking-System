package com.ats.simplifly.model;

import com.ats.simplifly.model.enums.SeatClassType;
import com.ats.simplifly.model.enums.SeatStatus;
import jakarta.persistence.*;

@Entity
@Table(name = "seats")
public class Seats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    private Schedule schedule;
    @Column(name = "class_type")
    @Enumerated(EnumType.STRING)
    private SeatClassType seatClassType;
    @Enumerated(EnumType.STRING)
    private SeatStatus seatStatus;

    public int getId() {
        return id;
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
