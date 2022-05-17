package com.example.leave_application.service;

import com.example.leave_application.DTO.LeaveTypeDTO;
import com.example.leave_application.entity.LeaveType;

import java.util.List;

public interface LeaveTypeService {
    LeaveType createLeaveType(LeaveType leaveType);
    List<LeaveTypeDTO>findAllLeaveType();
}
