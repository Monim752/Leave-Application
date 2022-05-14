package com.example.leave_application.repository;

import com.example.leave_application.entity.LeaveType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeaveTypeRepository extends JpaRepository<LeaveType, Long> {
    LeaveType findLeaveTypeByLeaveTypeName(String leaveType);
}
