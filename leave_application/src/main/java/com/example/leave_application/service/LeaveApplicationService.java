package com.example.leave_application.service;

import com.example.leave_application.DTO.Manager.ManagerRemarks;
import com.example.leave_application.DTO.User.LeaveApplicationDTO;
import com.example.leave_application.DTO.User.UpdateLeaveApplication;
import com.example.leave_application.entity.LeaveApplication;
import com.example.leave_application.enums.LeaveStatus;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

public interface LeaveApplicationService {


    @Transactional
    LeaveApplication createLeaveApplication(LeaveApplicationDTO leaveRequest, String leaveType);

    List<LeaveApplication> findAll();

    List<LeaveApplication> findAllLeaveApplications();

    @Transactional
    String approveLeaveApplication(Long userId, LeaveStatus leaveStatus);

    @Transactional
    String updateLeaveApplication(UpdateLeaveApplication updateLeaveApplication, Long id);

    String putRemarks(ManagerRemarks managerRemarks, Long id);

    List<LeaveApplication> findLeaveApplicationByDateRange(Date fromDate, Date toDate);

    List<LeaveApplication> findLeaveApplicationByLeaveTypeLeaveTypeName(String leaveType);

    List<LeaveApplication> findLeaveApplicationByLeaveStatus(LeaveStatus leaveStatus);

    List<LeaveApplication> showAllPendingLeaves();

    LeaveApplication findLeaveApplicationById(Long id);

    List<LeaveApplication> findLeaveApplicationByUserUserIdAndLeaveStatus(Long userId, LeaveStatus leaveStatus);

    int showLeaveBalance(String leaveType);

    int showLeaveBalanceOfUsers(String email, String leaveType);

    int showLeaveBalanceByLeaveType(String leaveType);

}
