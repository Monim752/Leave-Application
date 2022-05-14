package com.example.leave_application.service;

import com.example.leave_application.DTO.YearlyLeaveDTO;
import com.example.leave_application.entity.LeaveType;
import com.example.leave_application.entity.YearlyLeave;

import java.util.List;

public interface YearlyLeaveService {
    String allocateYearlyLeave(int year, String leaveType, int maximumLeave);
    YearlyLeave findYearlyLeaveById(Long id);
    YearlyLeave findYearlyLeaveByMaximumDay(int maximumDay);
    List<YearlyLeave> findYearlyLeaveByLeaveTypeLeaveTypeId(Long leaveTypeId);

}
