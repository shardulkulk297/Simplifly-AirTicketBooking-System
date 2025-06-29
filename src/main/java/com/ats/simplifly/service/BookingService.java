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

import java.util.ArrayList;
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
    private final ScheduleService scheduleService;
    private final FlightService flightService;
    private BookingRepository bookingRepository;

    public BookingService(BookingRepository bookingRepository, SeatService seatService, SeatRepository seatRepository, ScheduleRepository scheduleRepository, PassengerRepository passengerRepository, PassengerService passengerService, CustomerRepository customerRepository, PaymentService paymentService, BookingSeatRepository bookingSeatRepository, BookingSeatService bookingSeatService, ScheduleService scheduleService, FlightService flightService) {
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
        this.scheduleService = scheduleService;
        this.flightService = flightService;
    }



    public BookingDto bookTickets(BookingRequestDto bookingRequestDto, String username){
        /*
        Creating Booking POJO
         */
        Booking booking = new Booking();
        /*
        Getting Schedule of flight
         */
        Schedule schedule = scheduleRepository.findById(bookingRequestDto.getScheduleId()).orElseThrow(()->new ResourceNotFoundException("Schedule Not found"));
        /*
        Setting Schedule
         */
        booking.setSchedule(schedule);
        /*
        Getting Customer by Username
         */
        Customer customer = customerRepository.getByUsername(username);
        if(customer==null){
            throw new ResourceNotFoundException("Customer Not found");
        }
        booking.setCustomer(customer);
        List<Passenger> bookingPassengers = bookingRequestDto.getPassengers();
        int noOfTickets = bookingRequestDto.getNoOfTickets();
        /*
        Add Passengers to DB
         */
        List<Passenger> passengers = new ArrayList<>();
        for(Passenger p: bookingPassengers){
            Passenger passenger = passengerService.addPassenger(p, customer.getUser().getUsername());
            passengers.add(passenger);
        }

        /*
            Validate availability (Seat.status=AVAILABLE)
         */
        boolean checkAvailability = seatService.checkAvailableTickets(schedule, noOfTickets);
        if(checkAvailability){

            /*
            Mark N seats → HOLD
             */
            seatService.holdSeats(schedule, bookingRequestDto.getSeatNumbers());
            /*
            Calculate the total amount of booking
             */
            double totalAmount = seatService.calculateTotalPrice(schedule, bookingRequestDto.getSeatNumbers());

            if(totalAmount == 0){
                throw new RuntimeException("SOMETHING WENT WRONG WITH SEAT NUMBERS");
            }

            /*
                Create Booking (status=PENDING, noOfTickets)
             */
            booking.setBookingStatus(BookingStatus.PENDING);
            booking.setCustomer(customerRepository.getByUsername(username));
            booking.setTotalAmount(totalAmount);
            Booking savedBooking = bookingRepository.save(booking);

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
                savedBooking.setBookingStatus(BookingStatus.CONFIRMED);
               Booking bookingToSend = bookingRepository.save(savedBooking);
               /*
               Setting seatStatus to confirmed
                */
               seatService.confirmSeats(bookingRequestDto.getSeatNumbers(), schedule);
                /*
                Update total Seats amount
                 */
                Flight flight = schedule.getFlight();
                flight.setTotalSeats(flight.getTotalSeats() - bookingRequestDto.getNoOfTickets());
                flightService.updateFlight(flight, flight.getId());
               /*
               Setting DTO
                */
               BookingDto bookingDto = new BookingDto();
               bookingDto.setBookedBy(username);
               bookingDto.setBookingStatus(bookingToSend.getBookingStatus());
               bookingDto.setArrivalTime(schedule.getArrivalTime());
               bookingDto.setDepartureTime(schedule.getArrivalTime());
               bookingDto.setFlightNumber(schedule.getFlight().getFlightNumber());
               bookingDto.setRoute(schedule.getFlight().getRoute());
               bookingDto.setPassengerNames(passengers);
               bookingDto.setSeatNumbers(bookingRequestDto.getSeatNumbers());
               bookingDto.setTotalPrice(bookingToSend.getTotalAmount());
               return bookingDto;
            } else if (status.equals(PaymentStatus.FAILED)){
                /*
                On fail: flip seats → AVAILABLE, Booking.status=CANCELLED
                 */
                seatService.flipStatus(schedule);
                savedBooking.setBookingStatus(BookingStatus.CANCELLED);
                seatService.flipStatus(schedule);
                Booking bookingToSend = bookingRepository.save(savedBooking);
               /*
               Setting DTO
                */
                BookingDto bookingDto = new BookingDto();
                bookingDto.setBookedBy(username);
                bookingDto.setBookingStatus(bookingToSend.getBookingStatus());
                bookingDto.setArrivalTime(schedule.getArrivalTime());
                bookingDto.setDepartureTime(schedule.getArrivalTime());
                bookingDto.setFlightNumber(schedule.getFlight().getFlightNumber());
                bookingDto.setRoute(schedule.getFlight().getRoute());
                bookingDto.setPassengerNames(passengers);
                bookingDto.setSeatNumbers(bookingRequestDto.getSeatNumbers());
                bookingDto.setTotalPrice(bookingToSend.getTotalAmount());
                return bookingDto;
            }

        }
        else{
            throw new SeatsNotAvailableException("Sorry but seats are not available");
        }

        return null;
    }

    public List<BookingDto> getBookings(String name) {
        List<Booking> bookings = bookingRepository.getByCustomer(name);
        List<BookingDto> bookingDtos = new ArrayList<>();
        for(Booking booking: bookings){
            BookingDto bookingDto = new BookingDto();
            bookingDto.setBookingStatus(booking.getBookingStatus());
            bookingDto.setBookedBy(booking.getCustomer().getFullName());
            List<Passenger> passengers = bookingSeatRepository.getPassengersByBooking(booking.getId());
            bookingDto.setPassengerNames(passengers);
            bookingDto.setFlightNumber(booking.getSchedule().getFlight().getFlightNumber());
            bookingDto.setDepartureTime(booking.getSchedule().getDepartureTime());
            bookingDto.setRoute(booking.getSchedule().getFlight().getRoute());
            bookingDto.setArrivalTime(booking.getSchedule().getArrivalTime());
            bookingDto.setTotalPrice(booking.getTotalAmount());
            bookingDto.setSeatNumbers(bookingSeatRepository.getSeats(booking.getId()));
            bookingDtos.add(bookingDto);
        }
        return bookingDtos;
    }

//    public BookingDto cancelBooking(BookingRequestDto bookingRequestDto, String username){
//        /*
//        Creating Booking POJO
//         */
//        Booking booking = new Booking();
//
//        Schedule schedule = scheduleRepository.findById(bookingRequestDto.getSchedule().getId()).orElseThrow(()->new ResourceNotFoundException("Schedule Not found"));
//        List<String> seatNumbers = bookingRequestDto.getSeatNumbers();
//        List<Passenger> passengers = bookingRequestDto.getPassengers();
//        Customer customer = customerRepository.getByUsername(username);
//
//    }
}
