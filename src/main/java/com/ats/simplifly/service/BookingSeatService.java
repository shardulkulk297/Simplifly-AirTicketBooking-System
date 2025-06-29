package com.ats.simplifly.service;

import com.ats.simplifly.dto.*;
import com.ats.simplifly.model.*;
import com.ats.simplifly.repository.BookingRepository;
import com.ats.simplifly.repository.BookingSeatRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class BookingSeatService {

    private final BookingRepository bookingRepository;
    private final BookingSeatRepository bookingSeatRepository;

    public BookingSeatService(BookingRepository bookingRepository, BookingSeatRepository bookingSeatRepository) {
        this.bookingRepository = bookingRepository;
        this.bookingSeatRepository = bookingSeatRepository;
    }

    public List<BookingSeat> addBookingSeat(Map<Passenger, Seat> passengers, Booking booking){

        BookingSeat bookingSeat = new BookingSeat();
        List<BookingSeat> bookingSeats = new ArrayList<>();
        for(Map.Entry<Passenger, Seat> entry: passengers.entrySet()){
            Passenger passenger = entry.getKey();
            Seat seat = entry.getValue();
            bookingSeat.setPassenger(passenger);
            bookingSeat.setBooking(booking);
            bookingSeat.setSeat(seat);
            bookingSeat.setPrice(seat.getPrice());
            BookingSeat bookingSeatToSave = bookingSeatRepository.save(bookingSeat);
            bookingSeats.add(bookingSeat);
        }
        return bookingSeats;

    }

    public List<BookingSeatDto> getBookingSeatsByBooking(Booking booking) {
        List<BookingSeat> bookingSeats =  bookingSeatRepository.getBookingSeatsByBooking(booking.getId());
        List<BookingSeatDto> dtos = new ArrayList<>();
        for (BookingSeat bookingSeat : bookingSeats) {
            BookingSeatDto dto = new BookingSeatDto();

            dto.setId(bookingSeat.getId());
            dto.setPrice(bookingSeat.getPrice());

            // SeatDto
            Seat seat = bookingSeat.getSeat();
            SeatDto seatDto = new SeatDto();
            seatDto.setId(seat.getId());
            seatDto.setSeatNumber(seat.getSeatNumber());
            seatDto.setSeatClassType(seat.getSeatClassType());
            seatDto.setSeatStatus(seat.getSeatStatus());
            seatDto.setPrice(seat.getPrice());
            dto.setSeat(seatDto);

            // PassengerDto
            Passenger passenger = bookingSeat.getPassenger();
            PassengerDto passengerDto = new PassengerDto();
            passengerDto.setId(passenger.getId());
            passengerDto.setName(passenger.getName());
            passengerDto.setAge(passenger.getAge());
            passengerDto.setGender(passenger.getGender());
            passengerDto.setPassportNumber(passenger.getPassportNumber());
            passengerDto.setNationality(passenger.getNationality());

            // CustomerDto for passenger
            Customer customer = passenger.getCustomer();
            CustomerDto customerDto = new CustomerDto();
            customerDto.setId(customer.getId());
            customerDto.setFullName(customer.getFullName());
            customerDto.setEmail(customer.getEmail());
            customerDto.setContactNumber(customer.getContactNumber());
            customerDto.setAddress(customer.getAddress());
            customerDto.setImageLink(customer.getImageLink());

            // UserDto for customer
            UserDto userDto = new UserDto();
            userDto.setId(customer.getUser().getId());
            userDto.setUsername(customer.getUser().getUsername());
            userDto.setRole(customer.getUser().getRole());

            customerDto.setUser(userDto);
            passengerDto.setCustomer(customerDto);

            dto.setPassenger(passengerDto);

            dtos.add(dto);
        }

        return dtos;

    }
}
