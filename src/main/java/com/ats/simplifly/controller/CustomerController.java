package com.ats.simplifly.controller;

import com.ats.simplifly.model.Customer;
import com.ats.simplifly.model.FlightOwner;
import com.ats.simplifly.model.User;
import com.ats.simplifly.service.CustomerService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

@RestController
@CrossOrigin("http://localhost:5173")
public class CustomerController {

    private CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/api/customer/add")
    public ResponseEntity<?> addCustomer(@RequestBody Customer customer){
        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.addCustomer(customer));
    }

    @GetMapping("/api/customer/getCustomer")
    public ResponseEntity<?> getCustomer(Principal principal){
        String username = principal.getName();
        return ResponseEntity.status(HttpStatus.OK).body(customerService.getCustomer(username));
    }

    @PutMapping("/api/customer/editProfile")
    public ResponseEntity<?> editProfile(@RequestBody Customer customer, Principal principal){
        String username = principal.getName();
        User user = new User();
        user.setUsername(username);
        customer.setUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.editProfile(customer));
    }

    @DeleteMapping("/api/customer/delete/{customerId}")
    public ResponseEntity<?> deleteCustomer(@PathVariable int customerId){
        customerService.deleteCustomer(customerId);
        return ResponseEntity.status(HttpStatus.OK).body("DELETED SUCCESSFULLY");

    }

    @PutMapping("/api/customer/upload/image/{customerId}")
    public ResponseEntity<?> uploadLogo(@PathVariable int customerId, @RequestParam MultipartFile file) throws IOException {
        return ResponseEntity.status(HttpStatus.OK).body(customerService.uploadLogoSignUp(customerId, file));
    }
}
