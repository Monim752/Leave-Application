package com.example.leave_application.controller;


import com.example.leave_application.entity.LeaveApplication;
import com.example.leave_application.enums.LeaveStatus;
import com.example.leave_application.service.LeaveApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private LeaveApplicationService leaveApplicationService;

    @PostMapping("/applyForLeaveRequest")
    public LeaveApplication applyForLeaveRequest(@RequestBody com.example.leave_application.DTO.User.LeaveApplicationDTO leaveRequest){
        return leaveApplicationService.applyForLeaveRequest(leaveRequest);
    }

    @GetMapping("findLeaveApplications/{userId}/{leaveStatus}")
    public List<LeaveApplication> findLeaveApplicationByUserUserIdAndLeaveStatus(@PathVariable("userId") Long userId, @PathVariable("leaveStatus") LeaveStatus leaveStatus){
        return leaveApplicationService.findLeaveApplicationByUserUserIdAndLeaveStatus(userId, leaveStatus);
    }

}
