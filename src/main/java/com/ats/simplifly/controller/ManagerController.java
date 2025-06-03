package com.ats.simplifly.controller;

import com.ats.simplifly.exception.ResourceNotFoundException;
import com.ats.simplifly.model.PlatformManager;
import com.ats.simplifly.model.User;
import com.ats.simplifly.repository.ManagerRepository;
import com.ats.simplifly.repository.UserRepository;
import com.ats.simplifly.service.ManagerService;
import com.ats.simplifly.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ManagerController {

    @Autowired
    private ManagerService managerService;
    @Autowired
    private ManagerRepository managerRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @PostMapping("/api/manager/add")
    public PlatformManager addManager(@RequestBody PlatformManager manager){
        return managerService.addManager(manager);
    }

    @PostMapping("/api/manager/update/{managerId}")
    public PlatformManager updateManager(@RequestBody PlatformManager manager, int managerId) {
        PlatformManager managerToUpdate = managerRepository.findById(managerId).orElseThrow(()->new ResourceNotFoundException("Manager Not found"));

        if(manager.getFullName()!=null){
            managerToUpdate.setFullName(manager.getFullName());
        }
        if(manager.getEmail()!=null){
            managerToUpdate.setEmail(manager.getEmail());
        }
        return managerRepository.save(managerToUpdate);
    }




}
