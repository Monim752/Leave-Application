package com.example.leave_application.service;

import com.example.leave_application.entity.YearlyLeave;

public interface YearlyLeaveService {
    YearlyLeave createYearlyLeave(YearlyLeave yearlyLeave);
    YearlyLeave findYearlyLeaveById(Long id);
    YearlyLeave findYearlyLeaveByMaximumDay(int maximumDay);
    YearlyLeave findYearlyLeaveByLeaveTypeLeaveTypeId(Long leaveTypeId);
}
