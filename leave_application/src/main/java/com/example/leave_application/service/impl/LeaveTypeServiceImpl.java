package com.example.leave_application.service.impl;

import com.example.leave_application.entity.LeaveType;
import com.example.leave_application.repository.LeaveTypeRepository;
import com.example.leave_application.service.LeaveTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LeaveTypeServiceImpl implements LeaveTypeService {

    @Autowired
    private LeaveTypeRepository leaveTypeRepository;

    @Override
    public LeaveType createLeaveType(LeaveType leaveType) {
        if(leaveType!=null)
        {
            leaveTypeRepository.save(leaveType);
        }
        return null;
    }
}