package com.ats.simplifly.service;

import com.ats.simplifly.exception.ResourceNotFoundException;
import com.ats.simplifly.model.Flight;
import com.ats.simplifly.model.FlightOwner;
import com.ats.simplifly.model.User;
import com.ats.simplifly.model.enums.DocumentStatus;
import com.ats.simplifly.model.enums.Role;
import com.ats.simplifly.repository.FlightOwnerRepository;
import com.ats.simplifly.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class FlightOwnerService {


    private final UserService userService;
    private FlightOwnerRepository flightOwnerRepository;

    public FlightOwnerService(FlightOwnerRepository flightOwnerRepository,  UserService userService) {
        this.flightOwnerRepository = flightOwnerRepository;
        this.userService = userService;
    }

    public FlightOwner addOwner(FlightOwner flightOwner){
        User user = flightOwner.getUser();
        user.setRole(Role.FLIGHTOWNER);

        user = userService.signUp(user);

        flightOwner.setUser(user);
        flightOwner.setDocumentStatus(DocumentStatus.PENDING);
        return flightOwnerRepository.save(flightOwner);
    }

    public FlightOwner getOwner(String username){
        FlightOwner flightOwner = flightOwnerRepository.getByUsername(username);

        if(flightOwner == null){
            throw new ResourceNotFoundException("Flight Owner NOT FOUND");
        }

        return flightOwner;

    }

    public FlightOwner editProfile(FlightOwner flightOwner) {
        FlightOwner toUpdate = flightOwnerRepository
                .getByUsername(flightOwner.getUser().getUsername());
        if (toUpdate == null) {
            throw new ResourceNotFoundException(
                    "No flight-owner found with username "
                            + flightOwner.getUser().getUsername());
        }
        if (flightOwner.getCompanyName() != null) {
            toUpdate.setCompanyName(flightOwner.getCompanyName());
        }
        if (flightOwner.getEmail() != null) {
            toUpdate.setEmail(flightOwner.getEmail());
        }
        if (flightOwner.getContactPhone() != null) {
            toUpdate.setContactPhone(flightOwner.getContactPhone());
        }
        return flightOwnerRepository.save(toUpdate);
    }

    public void deleteOwner(int id){
        flightOwnerRepository.deleteById(id);
    }

}
