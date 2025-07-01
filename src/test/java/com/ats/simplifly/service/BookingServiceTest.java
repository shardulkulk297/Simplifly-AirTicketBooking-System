package com.ats.simplifly.service;

import com.ats.simplifly.dto.BookingDto;
import com.ats.simplifly.model.*;
import com.ats.simplifly.model.enums.*;
import com.ats.simplifly.repository.BookingRepository;
import com.ats.simplifly.repository.BookingSeatRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class BookingServiceTest {
    @InjectMocks
    private BookingService bookingService;

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private BookingSeatRepository bookingSeatRepository;

    private Booking booking;
    private BookingSeat bookingSeat;
    private Seat seat;
    private Passenger passenger;
    private Customer customer;
    private User user;
    private Schedule schedule;
    private Flight flight;
    private Route route;

    @BeforeEach
    public void init() {
        user = new User();
        user.setId(1);
        user.setUsername("john_doe");
        user.setRole(Role.CUSTOMER);

        customer = new Customer();
        customer.setId(1);
        customer.setFullName("John Doe");
        customer.setEmail("john.doe@email.com");
        customer.setContactNumber("1234567890");
        customer.setAddress("123 Main St");
        customer.setImageLink("image.jpg");
        customer.setUser(user);

        passenger = new Passenger();
        passenger.setId(1);
        passenger.setName("John Doe");
        passenger.setAge(30);
        passenger.setGender(Gender.MALE);
        passenger.setPassportNumber("AB1234567");
        passenger.setNationality("USA");
        passenger.setCustomer(customer);

        seat = new Seat();
        seat.setId(1);
        seat.setSeatNumber("A1");
        seat.setSeatClassType(SeatClassType.ECONOMY);
        seat.setSeatStatus(SeatStatus.BOOKED);
        seat.setPrice(250.0);

        route = new Route();
        route.setId(1);
        route.setOrigin("New York");
        route.setDestination("London");

        flight = new Flight();
        flight.setId(1);
        flight.setFlightNumber("AA101");
        flight.setRoute(route);

        schedule = new Schedule();
        schedule.setId(1);
        schedule.setDepartureTime(LocalDateTime.of(2025, 6, 15, 10, 0));
        schedule.setArrivalTime(LocalDateTime.of(2025, 6, 15, 20, 0));
        schedule.setFlight(flight);

        booking = new Booking();
        booking.setId(1);
        booking.setCustomer(customer);
        booking.setSchedule(schedule);
        booking.setBookingStatus(BookingStatus.CONFIRMED);
        booking.setTotalAmount(500.0);

        bookingSeat = new BookingSeat();
        bookingSeat.setId(1);
        bookingSeat.setPrice(250.0);
        bookingSeat.setSeat(seat);
        bookingSeat.setPassenger(passenger);
    }

    @Test
    public void getBookingsTest() {

        List<Booking> bookings = List.of(booking);
        List<Passenger> passengers = List.of(passenger);
        List<String> seatNumbers = List.of("A1");

        when(bookingRepository.getByCustomer("John Doe")).thenReturn(bookings);
        when(bookingSeatRepository.getPassengersByBooking(booking.getId())).thenReturn(passengers);
        when(bookingSeatRepository.getSeats(booking.getId())).thenReturn(seatNumbers);


        BookingDto expected = new BookingDto();
        expected.setBookingStatus(booking.getBookingStatus());
        expected.setBookedBy(customer.getFullName());
        expected.setPassengerNames(passengers);
        expected.setFlightNumber(flight.getFlightNumber());
        expected.setDepartureTime(schedule.getDepartureTime());
        expected.setRoute(route);
        expected.setArrivalTime(schedule.getArrivalTime());
        expected.setTotalPrice(booking.getTotalAmount());
        expected.setSeatNumbers(seatNumbers);
        expected.setBookingId(booking.getId());


        List<BookingDto> actualList = bookingService.getBookings("John Doe");


        assertEquals(1, actualList.size());
        assertEquals(expected, actualList.get(0));
    }
    @AfterEach
    public void afterTest() {
        booking = null;
        bookingSeat = null;
        seat = null;
        passenger = null;
        customer = null;
        user = null;
        schedule = null;
        flight = null;
        route = null;
    }

}