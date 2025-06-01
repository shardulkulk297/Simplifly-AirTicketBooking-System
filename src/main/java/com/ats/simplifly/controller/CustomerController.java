package com.ats.simplifly.controller;

import com.ats.simplifly.model.Customer;
import com.ats.simplifly.model.User;
import com.ats.simplifly.service.CustomerService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
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
}
