package com.ats.simplifly.service;

import com.ats.simplifly.dto.FlightDto;
import com.ats.simplifly.dto.FlightOwnerDto;
import com.ats.simplifly.dto.UserDto;
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
        flightOwner.setLogoLink("logo.png");
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
        
        when(flightOwnerRepository.getByUsername("owner_user"))
                .thenReturn(flightOwner);
        when(flightRepository.getAllFlights(flightOwner.getId()))
                .thenReturn(List.of(flight));

        
        FlightDto expected = new FlightDto();
        expected.setId(flight.getId());
        expected.setFlightNumber(flight.getFlightNumber());
        expected.setBaggageCheckin(flight.getBaggageCheckin());
        expected.setBaggageCabin(flight.getBaggageCabin());
        expected.setTotalSeats(flight.getTotalSeats());
        expected.setFirstClassSeats(flight.getFirstClassSeats());
        expected.setBusinessClassSeats(flight.getBusinessClassSeats());

        
        FlightOwnerDto ownerDto = new FlightOwnerDto();
        ownerDto.setId(flightOwner.getId());
        ownerDto.setCompanyName(flightOwner.getCompanyName());
        ownerDto.setEmail(flightOwner.getEmail());
        ownerDto.setContactPhone(flightOwner.getContactPhone());
        ownerDto.setVerificationStatus(flightOwner.getVerificationStatus());
        ownerDto.setLogoLink(flightOwner.getLogoLink());
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setRole(user.getRole());
        ownerDto.setUser(userDto);
        expected.setOwner(ownerDto);

        
        expected.setRoute(route);

        
        List<FlightDto> actualList = flightService.getAllFlights("owner_user");

        
        assertEquals(1, actualList.size());
        assertEquals(expected, actualList.get(0));
    }
    @AfterEach
    public void afterTest() {
        flight = null;
        flightOwner = null;
        user = null;
        route = null;
    }
}