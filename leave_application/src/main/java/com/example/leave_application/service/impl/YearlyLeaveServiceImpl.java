package com.example.leave_application.service.impl;

import com.example.leave_application.entity.LeaveType;
import com.example.leave_application.entity.YearlyLeave;
import com.example.leave_application.exception.LeaveTypeNotFoundException;
import com.example.leave_application.repository.LeaveTypeRepository;
import com.example.leave_application.repository.YearlyLeaveRepository;
import com.example.leave_application.service.YearlyLeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class YearlyLeaveServiceImpl implements YearlyLeaveService {

    @Autowired
    private YearlyLeaveRepository yearlyLeaveRepository;
    @Autowired
    private LeaveTypeRepository leaveTypeRepository;
    @Override
    public String allocateYearlyLeave(int year, String leaveType, int maximumLeave) {
        LeaveType leave=leaveTypeRepository.findLeaveTypeByLeaveTypeName(leaveType);
        if(leave==null){
            throw new LeaveTypeNotFoundException("LeaveType not found!!");
        }
        YearlyLeave yearlyLeave=new YearlyLeave(year,maximumLeave,new LeaveType(leave.getLeaveTypeId()));
        yearlyLeaveRepository.save(yearlyLeave);

        return "YearlyLeave allocated";
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
    public List<YearlyLeave> findYearlyLeaveByLeaveTypeLeaveTypeId(Long leaveTypeId) {
        return yearlyLeaveRepository.findYearlyLeaveByLeaveTypeLeaveTypeId(leaveTypeId);
    }
}
