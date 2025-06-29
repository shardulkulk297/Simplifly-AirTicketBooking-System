package com.ats.simplifly.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;

import com.ats.simplifly.dto.BookingSeatDto;
import com.ats.simplifly.model.*;
import com.ats.simplifly.model.enums.Gender;
import com.ats.simplifly.model.enums.Role;
import com.ats.simplifly.model.enums.SeatClassType;
import com.ats.simplifly.model.enums.SeatStatus;
import com.ats.simplifly.repository.BookingSeatRepository;
import com.ats.simplifly.service.BookingSeatService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;



@SpringBootTest
public class BookingSeatServiceTest {

    @InjectMocks
    private BookingSeatService bookingSeatService;

    @Mock
    private BookingSeatRepository bookingSeatRepository;

    private Booking booking;
    private BookingSeat bookingSeat;
    private Seat seat;
    private Passenger passenger;
    private Customer customer;
    private User user;

    @BeforeEach
    public void init() {
        // Create User
        user = new User();
        user.setId(1);
        user.setUsername("john_doe");
        user.setRole(Role.CUSTOMER);


        // Create Customer
        customer = new Customer();
        customer.setId(1);
        customer.setFullName("John Doe");
        customer.setEmail("john.doe@email.com");
        customer.setContactNumber("1234567890");
        customer.setAddress("123 Main St");
        customer.setImageLink("http://example.com/image.jpg");
        customer.setUser(user);

        // Create Passenger
        passenger = new Passenger();
        passenger.setId(1);
        passenger.setName("John Doe");
        passenger.setAge(30);
        passenger.setGender(Gender.MALE);
        passenger.setPassportNumber("AB1234567");
        passenger.setNationality("USA");
        passenger.setCustomer(customer);


        // Create Seat
        seat = new Seat();
        seat.setId(1);
        seat.setSeatNumber("A1");
        seat.setSeatClassType(SeatClassType.ECONOMY);
        seat.setSeatStatus(SeatStatus.BOOKED);
        seat.setPrice(250.0);


        // Create Booking
        booking = new Booking();
        booking.setId(1);

        // Create BookingSeat
        bookingSeat = new BookingSeat();
        bookingSeat.setId(1);
        bookingSeat.setPrice(250.0);
        bookingSeat.setSeat(seat);
        bookingSeat.setPassenger(passenger);

    }

    @Test
    public void getBookingSeatsByBookingTest() {
        /* Prepare mocked expected result */
        List<BookingSeat> expectedBookingSeats = List.of(bookingSeat);

        when(bookingSeatRepository.getBookingSeatsByBooking(booking.getId())).thenReturn(expectedBookingSeats);

        /* Actual call to service method */
        List<BookingSeatDto> actualList = bookingSeatService.getBookingSeatsByBooking(booking);

        /* Assertions */
        assertEquals(1, actualList.size());

        BookingSeatDto actualDto = actualList.get(0);
        assertEquals(bookingSeat.getId(), actualDto.getId());
        assertEquals(bookingSeat.getPrice(), actualDto.getPrice());

        // Verify nested objects exist
        assertEquals(seat.getId(), actualDto.getSeat().getId());
        assertEquals(passenger.getId(), actualDto.getPassenger().getId());
        assertEquals(customer.getId(), actualDto.getPassenger().getCustomer().getId());
        assertEquals(user.getId(), actualDto.getPassenger().getCustomer().getUser().getId());
    }

    @AfterEach
    public void afterTest() {
        booking = null;
        bookingSeat = null;
        seat = null;
        passenger = null;
        customer = null;
        user = null;
    }
}