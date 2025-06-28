package com.ats.simplifly.service;

import com.ats.simplifly.exception.ResourceNotFoundException;
import com.ats.simplifly.exception.UsernamAlreadyExistsException;
import com.ats.simplifly.model.FlightOwner;
import com.ats.simplifly.model.User;
import com.ats.simplifly.model.enums.VerificationStatus;
import com.ats.simplifly.model.enums.Role;
import com.ats.simplifly.repository.FlightOwnerRepository;
import com.ats.simplifly.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
public class FlightOwnerService {


    private final UserService userService;
    private final UserRepository userRepository;
    private FlightOwnerRepository flightOwnerRepository;

    public FlightOwnerService(FlightOwnerRepository flightOwnerRepository, UserService userService, UserRepository userRepository) {
        this.flightOwnerRepository = flightOwnerRepository;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    /*
    1. First get the user
    2. Set the role
    3. Save the user
    4. Save the Owner
     */
    public FlightOwner addOwner(FlightOwner flightOwner) {
        User check = userRepository.getByUsername(flightOwner.getUser().getUsername());
        if(check!=null){
            throw new UsernamAlreadyExistsException("Username Already exists!!");
        }
        User user = flightOwner.getUser();
        if(user == null){
            throw new ResourceNotFoundException("User is null pass user");
        }
        user.setRole(Role.FLIGHTOWNER);

        user = userService.signUp(user);

        flightOwner.setUser(user);
        flightOwner.setVerificationStatus(VerificationStatus.PENDING);


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

    public FlightOwner uploadLogo(MultipartFile file, String name) throws IOException{

        FlightOwner flightOwner = flightOwnerRepository.getByUsername(name);
        /*
        Getting Original Name
         */
        String originalFileName = file.getOriginalFilename();
        /*
        Extracting Extension
         */
        String extension = originalFileName.split("\\.")[1];
        /*
        Checking whether the extension is valid or not
         */
        if (!(List.of("jpg", "jpeg", "png", "gif", "svg").contains(extension))){
            throw new RuntimeException("File Extension " + extension + " not allowed " + "Allowed Extensions"
                    + List.of("jpg", "jpeg", "png", "gif", "svg"));
        }

        /*
        Checking if the directory exists else create one
         */
        String uploadFolder = "D:\\My Codes\\My Projects\\simplifly_frontend\\public\\images";
        Files.createDirectories(Path.of(uploadFolder));
        /*
        Define Full Path
         */
        Path path = Paths.get(uploadFolder, "\\", originalFileName);
        /*
        Upload file in mentioned Path, if already exisitng replace existing
         */
        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        flightOwner.setLogoLink(originalFileName);
        return flightOwnerRepository.save(flightOwner);
    }


    public FlightOwner uploadLogoSignUp(int id, MultipartFile file) throws IOException {
        FlightOwner flightOwner = flightOwnerRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("FlightOwner Not found"));
        /*
        Getting Original Name
         */
        String originalFileName = file.getOriginalFilename();
        /*
        Extracting Extension
         */
        String extension = originalFileName.split("\\.")[1];
        /*
        Checking whether the extension is valid or not
         */
        if (!(List.of("jpg", "jpeg", "png", "gif", "svg").contains(extension))){
            throw new RuntimeException("File Extension " + extension + " not allowed " + "Allowed Extensions"
                    + List.of("jpg", "jpeg", "png", "gif", "svg"));
        }

        /*
        Checking if the directory exists else create one
         */
        String uploadFolder = "D:\\My Codes\\My Projects\\simplifly_frontend\\public\\images";
        Files.createDirectories(Path.of(uploadFolder));
        /*
        Define Full Path
         */
        Path path = Paths.get(uploadFolder, "\\", originalFileName);
        /*
        Upload file in mentioned Path, if already exisitng replace existing
         */
        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        flightOwner.setLogoLink(originalFileName);
        return flightOwnerRepository.save(flightOwner);
    }
}
