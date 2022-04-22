package com.example.leave_application.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/accessDenied")
    public String accessDenied()
    {
        return "Access denied!!";
    }
}
