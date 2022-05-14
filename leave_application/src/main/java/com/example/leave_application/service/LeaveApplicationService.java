package com.example.leave_application.service;

import com.example.leave_application.DTO.Manager.ManagerLeaveApplicationDTO;
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

    @Transactional
    LeaveApplication approveLeaveApplication(Long id, LeaveStatus leaveStatus);

    String updateLeaveApplication(UpdateLeaveApplication updateLeaveApplication, Long id);

    List<LeaveApplication> findLeaveApplicationByDateRange(Date fromDate, Date toDate);

    List<LeaveApplication> findLeaveApplicationByLeaveTypeLeaveTypeName(String leaveType);

    List<LeaveApplication> findLeaveApplicationByLeaveStatus(LeaveStatus leaveStatus);

    LeaveApplication findLeaveApplicationById(Long id);

    List<LeaveApplication> findLeaveApplicationByUserUserIdAndLeaveStatus(Long userId, LeaveStatus leaveStatus);

    int showLeaveBalance(String leaveType);

}
