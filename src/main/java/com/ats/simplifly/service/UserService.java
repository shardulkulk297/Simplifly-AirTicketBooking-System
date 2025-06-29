package com.ats.simplifly.service;

import com.ats.simplifly.dto.CustomerDto;
import com.ats.simplifly.dto.FlightOwnerDto;
import com.ats.simplifly.dto.UserDto;
import com.ats.simplifly.model.Customer;
import com.ats.simplifly.model.FlightOwner;
import com.ats.simplifly.model.PlatformManager;
import com.ats.simplifly.model.User;
import com.ats.simplifly.model.enums.Role;
import com.ats.simplifly.repository.CustomerRepository;
import com.ats.simplifly.repository.FlightOwnerRepository;
import com.ats.simplifly.repository.ManagerRepository;
import com.ats.simplifly.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {


    private final CustomerRepository customerRepository;
    private final FlightOwnerRepository flightOwnerRepository;
    private final ManagerRepository managerRepository;
    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, CustomerRepository customerRepository, FlightOwnerRepository flightOwnerRepository, ManagerRepository managerRepository){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.customerRepository = customerRepository;
        this.flightOwnerRepository = flightOwnerRepository;
        this.managerRepository = managerRepository;
    }
    public User signUp(User user){
        String password = user.getPassword();
        String encodedPassword = passwordEncoder.encode(password);
        user.setPassword(encodedPassword);
        return userRepository.save(user);
    }

    public User updateUser(User user, String username) {
        User userToUpdate = userRepository.getByUsername(username);

        if(user.getUsername()!=null){
            userToUpdate.setUsername(user.getUsername());
        }
        return userRepository.save(userToUpdate);
    }

    public Object getLoggedInUserDetails(String username) {

        User userToFind = userRepository.getByUsername(username);

        switch (userToFind.getRole())
        {
            case Role.CUSTOMER -> {
                Customer customer = customerRepository.getByUsername(username);
                CustomerDto customerDto = new CustomerDto();
                customerDto.setId(customer.getId());
                customerDto.setFullName(customer.getFullName());
                customerDto.setEmail(customer.getEmail());
                customerDto.setContactNumber(customer.getContactNumber());
                customerDto.setAddress(customer.getAddress());
                customerDto.setImageLink(customer.getImageLink());

                UserDto userDto = new UserDto();
                userDto.setId(customer.getUser().getId());
                userDto.setUsername(customer.getUser().getUsername());
                userDto.setRole(customer.getUser().getRole());
                customerDto.setUser(userDto);

                return customerDto;
            }
            case Role.FLIGHTOWNER -> {
                FlightOwner owner = flightOwnerRepository.getByUsername(username);
                FlightOwnerDto ownerDto = new FlightOwnerDto();
                ownerDto.setId(owner.getId());
                ownerDto.setCompanyName(owner.getCompanyName());
                ownerDto.setEmail(owner.getEmail());
                ownerDto.setContactPhone(owner.getContactPhone());
                ownerDto.setLogoLink(owner.getLogoLink());
                ownerDto.setVerificationStatus(owner.getVerificationStatus());

                UserDto userDto = new UserDto();
                userDto.setId(owner.getUser().getId());
                userDto.setUsername(owner.getUser().getUsername());
                userDto.setRole(owner.getUser().getRole());
                ownerDto.setUser(userDto);

                return ownerDto;

            }
            case Role.MANAGER -> {
                PlatformManager manager = managerRepository.getByUsername(username);
                return manager;
            }
            default -> {
                return null;
            }

        }

    }
}
