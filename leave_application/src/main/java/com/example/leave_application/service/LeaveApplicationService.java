package com.example.leave_application.service;

import com.example.leave_application.DTO.Manager.ManagerLeaveApplicationDTO;
import com.example.leave_application.DTO.User.LeaveApplicationDTO;
import com.example.leave_application.entity.LeaveApplication;
import com.example.leave_application.enums.LeaveStatus;

import javax.transaction.Transactional;
import java.util.List;

public interface LeaveApplicationService {

    @Transactional
    LeaveApplication applyForLeave(LeaveApplication leaveApplication);

    @Transactional
    LeaveApplication applyForLeaveRequest(LeaveApplicationDTO leaveRequest);

    @Transactional
    LeaveApplication updateLeaveApplication(Long id, LeaveApplication leaveApplication);

    List<LeaveApplication> findLeaveApplicationByLeaveStatus(LeaveStatus leaveStatus);

    List<LeaveApplication> findByUserUserId(Long userId);

    List<LeaveApplication> findLeaveApplicationByUserUserId(Long userId);

    int countLeaveApplicationsByUserUserId(Long userId);
}
