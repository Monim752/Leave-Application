package com.example.leave_application.repository;

import com.example.leave_application.entity.LeaveApplication;
import com.example.leave_application.enums.LeaveStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeaveApplicationRepository extends JpaRepository<LeaveApplication, Long> {

    LeaveApplication findLeaveApplicationById(Long id);

    List<LeaveApplication> findLeaveApplicationByLeaveStatus(LeaveStatus leaveStatus);

    List<LeaveApplication> findByUserUserId(Long userId);

    int countLeaveApplicationsByUserUserId(Long userId);

    List<LeaveApplication> findLeaveApplicationByUserUserId(Long userId);
}
