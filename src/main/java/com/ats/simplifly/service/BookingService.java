package com.ats.simplifly.service;

import com.ats.simplifly.dto.BookingDto;
import com.ats.simplifly.dto.BookingRequestDto;
import com.ats.simplifly.exception.ResourceNotFoundException;
import com.ats.simplifly.exception.SeatsNotAvailableException;
import com.ats.simplifly.model.*;
import com.ats.simplifly.model.enums.PaymentStatus;
import com.ats.simplifly.repository.*;
import org.springframework.stereotype.Service;

import com.ats.simplifly.model.enums.BookingStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BookingService {

    private final SeatService seatService;
    private final SeatRepository seatRepository;
    private final ScheduleRepository scheduleRepository;
    private final PassengerRepository passengerRepository;
    private final PassengerService passengerService;
    private final CustomerRepository customerRepository;
    private final PaymentService paymentService;
    private final BookingSeatRepository bookingSeatRepository;
    private final BookingSeatService bookingSeatService;
    private BookingRepository bookingRepository;

    public BookingService(BookingRepository bookingRepository, SeatService seatService, SeatRepository seatRepository, ScheduleRepository scheduleRepository, PassengerRepository passengerRepository, PassengerService passengerService, CustomerRepository customerRepository, PaymentService paymentService, BookingSeatRepository bookingSeatRepository, BookingSeatService bookingSeatService) {
        this.bookingRepository = bookingRepository;
        this.seatService = seatService;
        this.seatRepository = seatRepository;
        this.scheduleRepository = scheduleRepository;
        this.passengerRepository = passengerRepository;
        this.passengerService = passengerService;
        this.customerRepository = customerRepository;
        this.paymentService = paymentService;
        this.bookingSeatRepository = bookingSeatRepository;
        this.bookingSeatService = bookingSeatService;
    }



    public BookingDto bookTickets(BookingRequestDto bookingRequestDto, String username){
        Booking booking = new Booking();
        Schedule schedule = scheduleRepository.findById(bookingRequestDto.getSchedule().getId()).orElseThrow(()->new ResourceNotFoundException("Schedule Not found"));
        booking.setSchedule(bookingRequestDto.getSchedule());
        List<Passenger> passengers = bookingRequestDto.getPassengers();
        int noOfTickets = bookingRequestDto.getNoOfTickets();
        /*
            Validate availability (Seat.status=AVAILABLE)
         */
        boolean checkAvailability = seatService.checkAvailableTickets(bookingRequestDto.getSchedule(), noOfTickets);
        if(checkAvailability){

            /*
            Mark N seats → HOLD
             */
            seatService.holdSeats(schedule, bookingRequestDto.getSeatNumbers());
            /*
            Calculate the total amount of booking
             */
            double totalAmount = seatService.calculateTotalPrice(schedule, bookingRequestDto.getSeatNumbers());
            /*
                Create Booking (status=PENDING, noOfTickets)
             */
            booking.setBookingStatus(BookingStatus.PENDING);
            booking.setCustomer(customerRepository.getByUsername(username));
            booking.setTotalAmount(totalAmount);

            /*
               For each passengerId & held seat, create BookingSeat
             */
            Map<Passenger, Seat> mapSeats = passengerService.mapPassengersToSeats(schedule, passengers, bookingRequestDto.getSeatNumbers());
            List<BookingSeat> bookingSeats = bookingSeatService.addBookingSeat(mapSeats, booking);
            /*
            Call paymentService…
             */
            PaymentStatus status = paymentService.makePayment(booking, bookingRequestDto);
            if(status.equals(PaymentStatus.SUCCESS))
            {
                /*
                On success: flip seats → BOOKED, Booking.status=CONFIRMED
                 */
                booking.setBookingStatus(BookingStatus.CONFIRMED);
               Booking bookingToSave = bookingRepository.save(booking);
               /*
               Setting DTO
                */
               BookingDto bookingDto = new BookingDto();
               bookingDto.setBooking(bookingToSave);
               bookingDto.setBookingSeats(bookingSeats);
               return bookingDto;
            } else if (status.equals(PaymentStatus.FAILED)){
                /*
                On fail: flip seats → AVAILABLE, Booking.status=CANCELLED
                 */
                seatService.flipStatus(schedule);
                booking.setBookingStatus(BookingStatus.CANCELLED);
                seatService.flipStatus(schedule);
                Booking bookingToSave = bookingRepository.save(booking);
               /*
               Setting DTO
                */
                BookingDto bookingDto = new BookingDto();
                bookingDto.setBooking(bookingToSave);
                bookingDto.setBookingSeats(bookingSeats);
                return bookingDto;
            }
        }
        else{
            throw new SeatsNotAvailableException("Seats Are Not available");
        }

        return null;
    }
}
