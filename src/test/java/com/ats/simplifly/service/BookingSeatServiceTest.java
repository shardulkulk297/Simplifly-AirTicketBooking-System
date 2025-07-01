package com.ats.simplifly.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;

import com.ats.simplifly.dto.*;
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


        
        booking = new Booking();
        booking.setId(1);

        
        bookingSeat = new BookingSeat();
        bookingSeat.setId(1);
        bookingSeat.setPrice(250.0);
        bookingSeat.setSeat(seat);
        bookingSeat.setPassenger(passenger);

    }

    @Test
    public void getBookingSeatsByBookingTest() {
        
        List<BookingSeat> repoResult = List.of(bookingSeat);
        when(bookingSeatRepository.getBookingSeatsByBooking(booking.getId()))
                .thenReturn(repoResult);

        
        BookingSeatDto expectedDto = new BookingSeatDto();
        expectedDto.setId(bookingSeat.getId());
        expectedDto.setPrice(bookingSeat.getPrice());

        
        SeatDto seatDto = new SeatDto();
        seatDto.setId(seat.getId());
        seatDto.setSeatNumber(seat.getSeatNumber());
        seatDto.setSeatClassType(seat.getSeatClassType());
        seatDto.setSeatStatus(seat.getSeatStatus());
        seatDto.setPrice(seat.getPrice());
        expectedDto.setSeat(seatDto);

        
        UserDto userDto = new UserDto(user.getId(), user.getUsername(), user.getRole());

        CustomerDto customerDto = new CustomerDto();
        customerDto.setId(customer.getId());
        customerDto.setFullName(customer.getFullName());
        customerDto.setEmail(customer.getEmail());
        customerDto.setContactNumber(customer.getContactNumber());
        customerDto.setAddress(customer.getAddress());
        customerDto.setImageLink(customer.getImageLink());
        customerDto.setUser(userDto);

        PassengerDto passengerDto = new PassengerDto();
        passengerDto.setId(passenger.getId());
        passengerDto.setName(passenger.getName());
        passengerDto.setAge(passenger.getAge());
        passengerDto.setGender(passenger.getGender());
        passengerDto.setPassportNumber(passenger.getPassportNumber());
        passengerDto.setNationality(passenger.getNationality());
        passengerDto.setCustomer(customerDto);

        expectedDto.setPassenger(passengerDto);

        
        List<BookingSeatDto> actualList = bookingSeatService.getBookingSeatsByBooking(booking);

        
        assertEquals(1, actualList.size());
        assertEquals(expectedDto, actualList.get(0));
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