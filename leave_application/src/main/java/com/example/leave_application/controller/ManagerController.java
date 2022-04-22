package com.example.leave_application.controller;

import com.example.leave_application.DTO.Manager.ManagerLeaveApplicationDTO;
import com.example.leave_application.entity.LeaveApplication;
import com.example.leave_application.enums.LeaveStatus;
import com.example.leave_application.service.LeaveApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping({"/Manager/"})
public class ManagerController {

    @Autowired
    private LeaveApplicationService leaveApplicationService;

    @PreAuthorize("hasAuthority('MANAGER')")
    @PutMapping("/updateLeaveApplication/{id}")
    public ResponseEntity<LeaveApplication> updateLeaveApplication(@RequestBody LeaveApplication leaveApplication, @PathVariable("id") Long id){
        return new ResponseEntity<LeaveApplication>(leaveApplicationService.updateLeaveApplication(id, leaveApplication), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @GetMapping("/leaveWithStatus/{leaveStatus}")
    List<LeaveApplication> findLeaveApplicationByLeaveStatus(@PathVariable("leaveStatus") LeaveStatus leaveStatus){
        return leaveApplicationService.findLeaveApplicationByLeaveStatus(leaveStatus);
    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @GetMapping("/findAppliedLeave/{userId}")
    public List<LeaveApplication> findLeaveApplicationByUserUserId(@PathVariable("userId") Long userId){
        return leaveApplicationService.findLeaveApplicationByUserUserId(userId);
    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @GetMapping("findLeaveApplication/{id}")
    public LeaveApplication findLeaveApplicationById(@PathVariable("id") Long id){
        return leaveApplicationService.findLeaveApplicationById(id);
    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @GetMapping("/findLeaveApplicationByDateRange/{fromDate}/{toDate}")
    public List<LeaveApplication> findLeaveApplicationByFromDateBetweenAndToDate(@PathVariable("fromDate") Date fromDate, @PathVariable("toDate") Date toDate){
        return leaveApplicationService.findLeaveApplicationByDateRange(fromDate,toDate);
    }
}
