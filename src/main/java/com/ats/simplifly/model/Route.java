package com.ats.simplifly.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "route")
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String origin;
    private String destination;
    private String duration;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Route route)) return false;
        return id == route.id && Objects.equals(origin, route.origin) && Objects.equals(destination, route.destination) && Objects.equals(duration, route.duration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, origin, destination, duration);
    }
}
