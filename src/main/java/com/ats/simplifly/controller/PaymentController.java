package com.ats.simplifly.controller;

import com.ats.simplifly.exception.PaymentFailedException;
import com.ats.simplifly.model.Booking;
import com.ats.simplifly.model.Payment;
import com.ats.simplifly.model.enums.PaymentStatus;
import com.ats.simplifly.service.BookingService;
import com.ats.simplifly.service.PaymentService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {


    private BookingService bookingService;
    private PaymentService paymentService;

    public PaymentController(BookingService bookingService, PaymentService paymentService) {
        this.bookingService = bookingService;
        this.paymentService = paymentService;
    }

//    @PostMapping("/api/payments/add")
//    public  makePayment(@RequestBody Payment payment){
//        return paymentService.makePayment(payment);
//    }
}
