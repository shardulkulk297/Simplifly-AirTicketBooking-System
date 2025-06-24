package com.ats.simplifly.controller;

import com.ats.simplifly.model.User;
import com.ats.simplifly.service.UserService;
import com.ats.simplifly.utility.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/api/user/signup")
    public ResponseEntity<?> signUp(@RequestBody User user){
        return ResponseEntity.status(HttpStatus.OK).body(userService.signUp(userService.signUp(user)));
    }

    @GetMapping("/api/user/getToken")
    public ResponseEntity<?> getToken(Principal principal){
        JwtUtil jwtUtil = new JwtUtil();
        try{
            String token = jwtUtil.createToken(principal.getName());
            Map<String, String> map = new HashMap<>();
            map.put("token", token);
            return ResponseEntity.status(HttpStatus.OK).body(token);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @GetMapping("/api/user/getLoggedInUserDetails")
    public Object getLoggedInUser(Principal principal){

        String username = principal.getName();
        return userService.getLoggedInUserDetails(username);

    }
}
