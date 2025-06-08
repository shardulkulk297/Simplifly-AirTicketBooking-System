package com.ats.simplifly.service;

import com.ats.simplifly.dto.BookingRequestDto;
import com.ats.simplifly.exception.PaymentFailedException;
import com.ats.simplifly.model.Booking;
import com.ats.simplifly.model.Payment;
import com.ats.simplifly.model.enums.BookingStatus;
import com.ats.simplifly.model.enums.PaymentStatus;
import com.ats.simplifly.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;


    public PaymentService(PaymentRepository paymentRepository) {

        this.paymentRepository = paymentRepository;
    }

    public PaymentStatus makePayment(Booking booking, BookingRequestDto bookingRequestDto) {
        Payment payment = new Payment();
        payment.setBooking(booking);
        payment.setAmount(booking.getTotalAmount());
        payment.setPaymentTime(LocalTime.now());
        payment.setPaymentMethod(bookingRequestDto.getPaymentMethod());
        //calling payment gateway
        payment.setPaymentStatus(PaymentStatus.SUCCESS);
        paymentRepository.save(payment);
        return payment.getPaymentStatus();
    }
}
