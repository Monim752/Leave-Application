package com.example.leave_application.controller;


import com.example.leave_application.entity.LeaveApplication;
import com.example.leave_application.enums.LeaveStatus;
import com.example.leave_application.service.LeaveApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private LeaveApplicationService leaveApplicationService;

    @PostMapping("/applyForLeaveRequest")
    public LeaveApplication applyForLeaveRequest(@RequestBody com.example.leave_application.DTO.User.LeaveApplicationDTO leaveRequest){
        return leaveApplicationService.applyForLeaveRequest(leaveRequest);
    }

}
