package com.ats.simplifly.service;

import com.ats.simplifly.dto.FlightDto;
import com.ats.simplifly.model.Flight;
import com.ats.simplifly.model.FlightOwner;
import com.ats.simplifly.model.Route;
import com.ats.simplifly.model.User;
import com.ats.simplifly.model.enums.Role;
import com.ats.simplifly.repository.FlightOwnerRepository;
import com.ats.simplifly.repository.FlightRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class FlightServiceTest {

    @InjectMocks
    private FlightService flightService;

    @Mock
    private FlightRepository flightRepository;

    @Mock
    private FlightOwnerRepository flightOwnerRepository;

    private Flight flight;
    private FlightOwner flightOwner;
    private User user;
    private Route route;

    @BeforeEach
    public void init() {
        user = new User();
        user.setId(1);
        user.setUsername("owner_user");
        user.setRole(Role.FLIGHTOWNER);

        flightOwner = new FlightOwner();
        flightOwner.setId(1);
        flightOwner.setCompanyName("AirCorp");
        flightOwner.setEmail("owner@aircorp.com");
        flightOwner.setContactPhone("9876543210");
        flightOwner.setLogoLink("http://logo.com/logo.png");
        flightOwner.setUser(user);

        route = new Route();
        route.setId(1);
        route.setOrigin("New York");
        route.setDestination("Paris");

        flight = new Flight();
        flight.setId(1);
        flight.setFlightNumber("AC123");
        flight.setBaggageCheckin(20);
        flight.setBaggageCabin(7);
        flight.setTotalSeats(180);
        flight.setFirstClassSeats(10);
        flight.setBusinessClassSeats(20);
        flight.setOwner(flightOwner);
        flight.setRoute(route);
    }

    @Test
    public void getAllFlightsTest() {
        List<Flight> flights = List.of(flight);

        when(flightOwnerRepository.getByUsername("owner_user")).thenReturn(flightOwner);
        when(flightRepository.getAllFlights(flightOwner.getId())).thenReturn(flights);

        List<FlightDto> flightDtoList = flightService.getAllFlights("owner_user");

        assertEquals(1, flightDtoList.size());
        FlightDto dto = flightDtoList.get(0);


        assertEquals(flight.getId(), dto.getId());
        assertEquals(flight.getFlightNumber(), dto.getFlightNumber());
    }

    @AfterEach
    public void afterTest() {
        flight = null;
        flightOwner = null;
        user = null;
        route = null;
    }
}
