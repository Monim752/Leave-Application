package com.example.leave_application.controller;


import com.example.leave_application.DTO.LeaveTypeDTO;
import com.example.leave_application.DTO.User.UpdateLeaveApplication;
import com.example.leave_application.entity.LeaveApplication;
import com.example.leave_application.entity.User;
import com.example.leave_application.enums.LeaveStatus;
import com.example.leave_application.service.LeaveApplicationService;
import com.example.leave_application.service.LeaveTypeService;
import com.example.leave_application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping({"/api/"})
public class UserController {

    @Autowired
    private LeaveApplicationService leaveApplicationService;
    @Autowired
    private UserService userService;
    @Autowired
    private LeaveTypeService leaveTypeService;

    @GetMapping("/findUserByRolesRoleName/{roleName}")
    public List<User> findUserByRolesRoleName(@PathVariable("roleName") String roleName){
        return userService.findUserByRolesRoleName(roleName);
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/createLeaveApplication")
    public LeaveApplication createLeaveApplication(@RequestBody com.example.leave_application.DTO.User.LeaveApplicationDTO leaveRequest, @RequestParam("leaveType") String leaveType){
        return leaveApplicationService.createLeaveApplication(leaveRequest, leaveType);
    }

    @PreAuthorize("hasAuthority('USER')")
    @PutMapping("/updateLeaveApplication/{id}")
    public String updateLeaveApplication(@RequestBody UpdateLeaveApplication updateLeaveApplication, @PathVariable("id") Long id){
        return leaveApplicationService.updateLeaveApplication(updateLeaveApplication, id);
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/viewLeaveApplications/{leaveType}")
    public List<LeaveApplication> findLeaveApplicationByLeaveTypeLeaveTypeName(@PathVariable("leaveType") String leaveType){
        return leaveApplicationService.findLeaveApplicationByLeaveTypeLeaveTypeName(leaveType);
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/findLeaveApplications/{leaveStatus}")
    public List<LeaveApplication>findLeaveApplicationByLeaveStatus(@PathVariable("leaveStatus") LeaveStatus leaveStatus){
        return leaveApplicationService.findLeaveApplicationByLeaveStatus(leaveStatus);
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/findAllLeaveType")
    public List<LeaveTypeDTO> findAllLeaveType(){
        return leaveTypeService.findAllLeaveType();
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/showLeaveBalance/{leaveType}")
    public int showLeaveBalance(@PathVariable("leaveType") String leaveType){
        return leaveApplicationService.showLeaveBalance(leaveType);
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/findLeaveApplicationByDateRange/{fromDate}/{toDate}")
    public List<LeaveApplication> findLeaveApplicationByFromDateBetweenAndToDate(@PathVariable("fromDate") Date fromDate, @PathVariable("toDate") Date toDate){
        return leaveApplicationService.findLeaveApplicationByDateRange(fromDate,toDate);
    }

}
