package com.example.leave_application.controller;


import com.example.leave_application.entity.LeaveApplication;
import com.example.leave_application.entity.User;
import com.example.leave_application.enums.LeaveStatus;
import com.example.leave_application.service.LeaveApplicationService;
import com.example.leave_application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private LeaveApplicationService leaveApplicationService;
    @Autowired
    private UserService userService;

    @GetMapping("/findUserByRolesRoleName/{roleName}")
    public List<User> findUserByRolesRoleName(@PathVariable("roleName") String roleName){
        return userService.findUserByRolesRoleName(roleName);
    }

}
