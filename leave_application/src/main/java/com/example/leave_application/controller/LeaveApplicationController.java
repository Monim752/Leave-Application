package com.example.leave_application.controller;

import com.example.leave_application.DTO.SignUp;
import com.example.leave_application.DTO.User.LeaveApplicationDTO;
import com.example.leave_application.DTO.User.UpdateLeaveApplication;
import com.example.leave_application.DTO.YearlyLeaveDTO;
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

    @PostMapping("/signUp")
    public User saveData(@RequestBody SignUp signUp){
        String encodedPassword= passwordEncoder.encode(signUp.getPassword());
        signUp.setPassword(encodedPassword);
        return userService.saveData(signUp);
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

    @PreAuthorize("hasAnyAuthority('MANAGER','ADMIN')")
    @PatchMapping("/updateLeaveApplication/{id}")
    public LeaveApplication approveLeaveApplication(@PathVariable("id") Long id, @RequestParam("leaveStatus") LeaveStatus leaveStatus){
        return leaveApplicationService.approveLeaveApplication(id, leaveStatus);
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

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/createLeaveApplication")
    public LeaveApplication createLeaveApplication(@RequestBody com.example.leave_application.DTO.User.LeaveApplicationDTO leaveRequest, @RequestParam("leaveTyp") String leaveType){
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
    @GetMapping("findLeaveApplications/{leaveStatus}")
    public List<LeaveApplication>findLeaveApplicationByLeaveStatus(@PathVariable("leaveStatus") LeaveStatus leaveStatus){
        return leaveApplicationService.findLeaveApplicationByLeaveStatus(leaveStatus);
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/showLeaveBalance/{leaveType}")
    public int showLeaveBalance(@PathVariable("leaveType") String leaveType){
        return leaveApplicationService.showLeaveBalance(leaveType);
    }
}
