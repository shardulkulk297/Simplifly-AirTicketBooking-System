package com.ats.simplifly.service;

import com.ats.simplifly.exception.PaymentFailedException;
import com.ats.simplifly.model.Booking;
import com.ats.simplifly.model.Payment;
import com.ats.simplifly.model.enums.BookingStatus;
import com.ats.simplifly.model.enums.PaymentStatus;
import com.ats.simplifly.repository.PaymentRepository;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private BookingService bookingService;

    public PaymentService(BookingService bookingService, PaymentRepository paymentRepository) {
        this.bookingService = bookingService;
        this.paymentRepository = paymentRepository;
    }

    public Payment makePayment(Payment payment) {
//        payment.setPaymentStatus(PaymentStatus.SUCCESS);
//        String status = payment.getPaymentStatus().toString();
//        if(status.equals("SUCCESS")){
//            Booking booking = payment.getBooking();
//            booking.setBookingStatus(BookingStatus.CONFIRMED);
//            bookingService.makeBooking(booking);
//        }
//        else {
//            Booking booking = payment.getBooking();
//            booking.setBookingStatus(BookingStatus.PENDING);
//            bookingService.makeBooking(booking);
//        }
        return paymentRepository.save(payment);
    }
}
