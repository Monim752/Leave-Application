package com.example.leave_application.controller;

import com.example.leave_application.DTO.Manager.ManagerLeaveApplicationDTO;
import com.example.leave_application.entity.LeaveApplication;
import com.example.leave_application.enums.LeaveStatus;
import com.example.leave_application.service.LeaveApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ManagerController {
    @Autowired
    private LeaveApplicationService leaveApplicationService;

    @PatchMapping("/updateleaveApplication/{id}")
    public ResponseEntity<LeaveApplication> updateLeaveApplication(@RequestBody LeaveApplication leaveApplication, @PathVariable("id") Long id){
        return new ResponseEntity<LeaveApplication>(leaveApplicationService.updateLeaveApplication(id, leaveApplication), HttpStatus.OK);
    }

    @GetMapping("/leaveWithStatus/{leaveStatus}")
    List<LeaveApplication> findLeaveApplicationByLeaveStatus(@PathVariable("leaveStatus") LeaveStatus leaveStatus){
        return leaveApplicationService.findLeaveApplicationByLeaveStatus(leaveStatus);
    }

    @GetMapping("/findAppliedLeave/{userId}")
    public List<LeaveApplication> findLeaveApplicationByUserUserId(@PathVariable("userId") Long userId){
        return leaveApplicationService.findLeaveApplicationByUserUserId(userId);
    }
}
