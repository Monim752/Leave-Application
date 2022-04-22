package com.example.leave_application.controller;

import com.example.leave_application.DTO.Manager.ManagerLeaveApplicationDTO;
import com.example.leave_application.entity.LeaveApplication;
import com.example.leave_application.entity.LeaveType;
import com.example.leave_application.entity.User;
import com.example.leave_application.entity.YearlyLeave;
import com.example.leave_application.enums.LeaveStatus;
import com.example.leave_application.service.LeaveApplicationService;
import com.example.leave_application.service.LeaveTypeService;
import com.example.leave_application.service.UserService;
import com.example.leave_application.service.YearlyLeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AdminController {
    @Autowired
    private UserService userService;
    @Autowired
    private LeaveTypeService leaveTypeService;
    @Autowired
    private YearlyLeaveService yearlyLeaveService;
    @Autowired
    private LeaveApplicationService leaveApplicationService;

    @GetMapping("/users")
    public List<User> findAllUsers(){
        return userService.findAllUsers();
    }

    @PostMapping("/createLeaveType")
    public LeaveType createLeaveType(@RequestBody LeaveType leaveType){
        return leaveTypeService.createLeaveType(leaveType);
    }

    @PostMapping("/createYearlyLeave")
    public YearlyLeave createYearlyLeave(@RequestBody YearlyLeave yearlyLeave){
        return yearlyLeaveService.createYearlyLeave(yearlyLeave);
    }
}
