package com.example.leave_application.repository;

import com.example.leave_application.DTO.LeaveTypeDTO;
import com.example.leave_application.entity.LeaveType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeaveTypeRepository extends JpaRepository<LeaveType, Long> {
    LeaveType findLeaveTypeByLeaveTypeName(String leaveType);

    @Query("select l from LeaveType l")
    List<LeaveTypeDTO>findAllLeaveType();
}
