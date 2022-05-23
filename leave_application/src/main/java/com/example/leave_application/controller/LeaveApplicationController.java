package com.example.leave_application.controller;

import com.example.leave_application.DTO.Admin.Admin;
import com.example.leave_application.DTO.LeaveTypeDTO;
import com.example.leave_application.DTO.Manager.Manager;
import com.example.leave_application.DTO.Manager.ManagerRemarks;
import com.example.leave_application.DTO.UserInfo;
import com.example.leave_application.DTO.User.UpdateLeaveApplication;
import com.example.leave_application.entity.LeaveApplication;
import com.example.leave_application.entity.LeaveType;
import com.example.leave_application.entity.User;
import com.example.leave_application.enums.LeaveStatus;
import com.example.leave_application.service.LeaveApplicationService;
import com.example.leave_application.service.LeaveTypeService;
import com.example.leave_application.service.UserService;
import com.example.leave_application.service.YearlyLeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping({"/api/"})
public class LeaveApplicationController {

    @Autowired
    private LeaveApplicationService leaveApplicationService;
    @Autowired
    private YearlyLeaveService yearlyLeaveService;
    @Autowired
    private LeaveTypeService leaveTypeService;
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/allLeaveApplications")
    public List<LeaveApplication> findAll() {
        return leaveApplicationService.findAll();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/addUser")
    public User addUser(@RequestBody UserInfo signUp){
        String encodedPassword= passwordEncoder.encode(signUp.getPassword());
        signUp.setPassword(encodedPassword);
        return userService.addUser(signUp);
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/addAdmin")
    public User addAdmin(Admin admin){
        String encodedPassword= passwordEncoder.encode(admin.getPassword());
        admin.setPassword(encodedPassword);
        return userService.addAdmin(admin);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/addManager")
    public User addManager(Manager manager){
        String encodedPassword= passwordEncoder.encode(manager.getPassword());
        manager.setPassword(encodedPassword);
        return userService.addManager(manager);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/createLeaveType")
    public LeaveType createLeaveType(@RequestBody LeaveType leaveType) {
        return leaveTypeService.createLeaveType(leaveType);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/allocateYearlyLeave")
    public String allocateYearlyLeave(@RequestParam("year") int year, @RequestParam("leaveType") String leaveType, @RequestParam("maximumLeave") int maximumLeave) {
        return yearlyLeaveService.allocateYearlyLeave(year, leaveType, maximumLeave);
    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @PostMapping("/approveLeaveApplication/{id}")
    public String approveLeaveApplication(@PathVariable("id") Long id, @RequestParam("leaveStatus") LeaveStatus leaveStatus){
        return leaveApplicationService.approveLeaveApplication(id, leaveStatus);
    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @PutMapping("/putRemarks/{id}")
    public String putRemarks(@RequestBody ManagerRemarks managerRemarks, @PathVariable("id") Long id){
        return leaveApplicationService.putRemarks(managerRemarks, id);
    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @GetMapping("/findAllLeaveApplications")
    public List<LeaveApplication> findAllLeaveApplications(){
        return leaveApplicationService.findAllLeaveApplications();
    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @GetMapping("/showAllPendingLeaves")
    public List<LeaveApplication> showAllPendingLeaves(){
        return leaveApplicationService.showAllPendingLeaves();
    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @GetMapping("/showLeaveBalanceByLeaveType")
    public int showLeaveBalanceByLeaveType(@RequestParam("leaveType") String leaveType){
        return leaveApplicationService.showLeaveBalanceByLeaveType(leaveType);
    }

    @PreAuthorize("hasAnyAuthority('MANAGER','ADMIN')")
    @GetMapping("/showLeaveBalanceOfUsers/{email}")
    public int showLeaveBalanceOfUsers(@PathVariable("email") String email, @RequestParam("leaveType") String leaveType){
        return leaveApplicationService.showLeaveBalanceOfUsers(email, leaveType);
    }

}
