package com.ats.simplifly.service;

import com.ats.simplifly.dto.CustomerDto;
import com.ats.simplifly.dto.UserDto;
import com.ats.simplifly.exception.ResourceNotFoundException;
import com.ats.simplifly.model.Customer;
import com.ats.simplifly.model.FlightOwner;
import com.ats.simplifly.model.User;
import com.ats.simplifly.model.enums.Role;
import com.ats.simplifly.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
public class CustomerService {

    private final UserService userService;
    private final CustomerRepository customerRepository;

    public CustomerService(UserService userService, CustomerRepository customerRepository) {
        this.userService = userService;
        this.customerRepository = customerRepository;
    }

    public CustomerDto addCustomer(Customer customer) {
        User user = customer.getUser(); //Getting the customer which is without role and plain text password
        user.setRole(Role.CUSTOMER); //setting the role
        user = userService.signUp(user); //Adding user to user table and encrypting the password
        customer.setUser(user);//setting the new user
        Customer savedCustomer = customerRepository.save(customer); //saving the customer


        CustomerDto dto = new CustomerDto();
        dto.setId(savedCustomer.getId());
        dto.setFullName(savedCustomer.getFullName());
        dto.setEmail(savedCustomer.getEmail());
        dto.setContactNumber(savedCustomer.getContactNumber());
        dto.setAddress(savedCustomer.getAddress());


        // Map nested User to UserDto
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setRole(user.getRole());

        dto.setUser(userDto);

        return dto;
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

    public CustomerDto uploadLogoSignUp(int id, MultipartFile file) throws IOException {
        Customer customer = customerRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Customer Not found"));
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
        customer.setImageLink(originalFileName);
        Customer savedCustomer =  customerRepository.save(customer);
        User user = savedCustomer.getUser();
        CustomerDto dto = new CustomerDto();
        dto.setId(savedCustomer.getId());
        dto.setFullName(savedCustomer.getFullName());
        dto.setEmail(savedCustomer.getEmail());
        dto.setContactNumber(savedCustomer.getContactNumber());
        dto.setAddress(savedCustomer.getAddress());


        // Map nested User to UserDto
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setRole(user.getRole());

        dto.setUser(userDto);

        return dto;
    }


}
