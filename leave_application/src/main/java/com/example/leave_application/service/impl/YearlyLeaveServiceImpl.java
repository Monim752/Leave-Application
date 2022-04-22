package com.example.leave_application.service.impl;

import com.example.leave_application.entity.YearlyLeave;
import com.example.leave_application.repository.YearlyLeaveRepository;
import com.example.leave_application.service.YearlyLeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class YearlyLeaveServiceImpl implements YearlyLeaveService {

    @Autowired
    private YearlyLeaveRepository yearlyLeaveRepository;

    @Override
    public YearlyLeave createYearlyLeave(YearlyLeave yearlyLeave) {

        return yearlyLeaveRepository.save(yearlyLeave);
    }

    @Override
    public YearlyLeave findYearlyLeaveById(Long id) {
        return yearlyLeaveRepository.findYearlyLeaveById(id);
    }

    @Override
    public YearlyLeave findYearlyLeaveByMaximumDay(int maximumDay) {
        return yearlyLeaveRepository.findYearlyLeaveByMaximumDay(maximumDay);
    }

    @Override
    public YearlyLeave findYearlyLeaveByLeaveTypeLeaveTypeId(Long leaveTypeId) {
        return yearlyLeaveRepository.findYearlyLeaveByLeaveTypeLeaveTypeId(leaveTypeId);
    }
}
