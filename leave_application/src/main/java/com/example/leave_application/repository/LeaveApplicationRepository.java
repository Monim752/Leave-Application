package com.example.leave_application.repository;

import com.example.leave_application.entity.LeaveApplication;
import com.example.leave_application.entity.User;
import com.example.leave_application.enums.LeaveStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface LeaveApplicationRepository extends JpaRepository<LeaveApplication, Long> {

    LeaveApplication findLeaveApplicationById(Long id);
    LeaveApplication findLeaveApplicationByIdAndUserUserId(Long id, Long userId);
    List<LeaveApplication> findAll();

    List<LeaveApplication> findLeaveApplicationByUserUserId(Long userId);

    List<LeaveApplication> findLeaveApplicationByLeaveStatus(LeaveStatus leaveStatus);

    List<LeaveApplication> findLeaveApplicationByLeaveStatusAndUserUserId(LeaveStatus leaveStatus, Long userId);

    List<LeaveApplication> findLeaveApplicationByLeaveTypeLeaveTypeNameAndUserUserId(String leaveType, Long userId);

    List<LeaveApplication> findLeaveApplicationByFromDateBetweenAndUserUserId(Date fromDate, Date toDate, Long userId);

    List<LeaveApplication> findLeaveApplicationByUserUserIdAndLeaveStatus(Long userId, LeaveStatus leaveStatus);

    int countLeaveApplicationsByUserUserId(Long userId);



}
