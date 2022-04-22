package com.example.leave_application.controller;

import com.example.leave_application.DTO.SignUp;
import com.example.leave_application.entity.User;
import com.example.leave_application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SignUpController {

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/signUp")
    public User saveData(@RequestBody SignUp signUp){
        String encodedPassword= passwordEncoder.encode(signUp.getPassword());
        signUp.setPassword(encodedPassword);
        return userService.saveData(signUp);
    }
}
