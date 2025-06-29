package com.ats.simplifly.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.ats.simplifly.dto.ScheduleDto;
import com.ats.simplifly.model.*;
import com.ats.simplifly.model.enums.Role;
import com.ats.simplifly.model.enums.VerificationStatus;
import com.ats.simplifly.repository.ScheduleRepository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;

@SpringBootTest
public class ScheduleServiceTest {

    @InjectMocks
    private ScheduleService scheduleService;

    @Mock
    private ScheduleRepository scheduleRepository;

    private Schedule schedule;
    private Route route;
    private Flight flight;
    private FlightOwner owner;
    private User user;

    @BeforeEach
    public void init() {
        user = new User();
        user.setId(1);
        user.setUsername("flight_owner_1");
        user.setRole(Role.FLIGHTOWNER);

        owner = new FlightOwner();
        owner.setId(1);
        owner.setCompanyName("SkyJet Airlines");
        owner.setEmail("owner@skyjet.com");
        owner.setContactPhone("1234567890");
        owner.setVerificationStatus(VerificationStatus.APPROVED);
        owner.setLogoLink("http://logo.com/image.jpg");
        owner.setUser(user);

        route = new Route();
        route.setId(1);
        route.setOrigin("NYC");
        route.setDestination("LAX");

        flight = new Flight();
        flight.setId(1);
        flight.setFlightNumber("FL123");
        flight.setRoute(route);
        flight.setOwner(owner);

        schedule = new Schedule();
        schedule.setId(1);
        schedule.setDepartureTime(LocalDateTime.now().plusHours(2));
        schedule.setArrivalTime(LocalDateTime.now().plusHours(5));
        schedule.setFlight(flight);
    }

    @Test
    public void getFlightSearchTest() {
        LocalDate date = LocalDate.of(2024, 1, 1);
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.plusDays(1).atStartOfDay();

        Pageable pageable = PageRequest.of(0, 5);
        List<Schedule> scheduleList = List.of(schedule);
        Page<Schedule> page = new PageImpl<>(scheduleList);

        when(scheduleRepository.searchFlight("NYC", "LAX", start, end, pageable)).thenReturn(page);

        List<ScheduleDto> result = scheduleService.getFlightSearch("NYC", "LAX", date.toString(), 0, 5);

        assertEquals(1, result.size());
        assertEquals(schedule.getId(), result.get(0).getId());
    }

    @Test
    public void getScheduleTest() {
        when(scheduleRepository.findById(1)).thenReturn(Optional.of(schedule));

        ScheduleDto result = scheduleService.getSchedule(1);

        assertEquals(schedule.getId(), result.getId());
    }

    @Test
    public void getScheduleInvalidIdTest() {
        assertThrows(RuntimeException.class, () -> scheduleService.getSchedule(0));
    }

    @Test
    public void getScheduleNotFoundTest() {
        when(scheduleRepository.findById(2)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> scheduleService.getSchedule(2));
    }

    @AfterEach
    public void afterTest() {
        schedule = null;
        flight = null;
        route = null;
        owner = null;
        user = null;
    }
}
