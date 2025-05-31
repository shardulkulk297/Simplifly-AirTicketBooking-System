package com.ats.simplifly.service;

import com.ats.simplifly.model.Customer;
import com.ats.simplifly.model.User;
import com.ats.simplifly.model.enums.Role;
import com.ats.simplifly.repository.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private final UserService userService;
    private final CustomerRepository customerRepository;

    public CustomerService(UserService userService, CustomerRepository customerRepository) {
        this.userService = userService;
        this.customerRepository = customerRepository;
    }

    public Customer addCustomer(Customer customer){
        User user = customer.getUser(); //Getting the customer which is without role and plain text password
        user.setRole(Role.CUSTOMER); //setting the role
        user = userService.signUp(user); //Adding user to user table and encrypting the password
        customer.setUser(user); //setting the new user

        return customerRepository.save(customer); //saving the customer
    }

    public Customer getCustomer(String username){

        return customerRepository.getByUsername(username);

    }

    public Customer editProfile(Customer customer){
        Customer toUpdateCust = customerRepository.getByUsername(customer.getUser().getUsername());
        if(customer.getFullName() != null){
            toUpdateCust.setFullName(customer.getFullName());
        }
        if(customer.getAddress()!=null){
            toUpdateCust.setAddress(customer.getAddress());
        }
        if(customer.getEmail()!=null){
            toUpdateCust.setEmail(customer.getEmail());
        }
        if(customer.getContactNumber()!=null){
            toUpdateCust.setContactNumber(customer.getContactNumber());
        }
        return customerRepository.save(toUpdateCust);
    }

    public void deleteCustomer(int id){
        customerRepository.deleteById(id);
    }


}
