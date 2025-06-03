package com.ats.simplifly.service;

import com.ats.simplifly.model.PlatformManager;
import com.ats.simplifly.model.User;
import com.ats.simplifly.model.enums.Role;
import com.ats.simplifly.repository.ManagerRepository;
import com.ats.simplifly.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class ManagerService {


    private final UserService userService;
    private final ManagerRepository managerRepository;

    public ManagerService(UserService userService, ManagerRepository managerRepository) {
        this.userService = userService;
        this.managerRepository = managerRepository;
    }

    public PlatformManager addManager(PlatformManager manager) {
        User user = manager.getUser();
        user.setRole(Role.MANAGER);
        user = userService.signUp(user);
        manager.setUser(user);
        return managerRepository.save(manager);
    }

    public PlatformManager editProfile(PlatformManager manager, String username){
        PlatformManager managerToUpdate = managerRepository.getByUsername(username);

        if(manager.getFullName()!=null){
            managerToUpdate.setFullName(manager.getFullName());
        }
        if(manager.getEmail()!=null){
            managerToUpdate.setEmail(manager.getEmail());
        }
        return managerRepository.save(managerToUpdate);
    }
}
