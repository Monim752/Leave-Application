package com.example.leave_application.service.impl;

import com.example.leave_application.entity.LeaveApplication;
import com.example.leave_application.entity.YearlyLeave;
import com.example.leave_application.enums.LeaveStatus;
import com.example.leave_application.exception.BalanceNotAvailableException;
import com.example.leave_application.exception.LeaveApplicationNotFoundException;
import com.example.leave_application.repository.LeaveApplicationRepository;
import com.example.leave_application.service.LeaveApplicationService;
import com.example.leave_application.service.YearlyLeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class LeaveApplicationServiceImpl implements LeaveApplicationService {

    @Autowired
    private LeaveApplicationRepository leaveApplicationRepository;

    @Autowired
    private YearlyLeaveService yearlyLeaveService;

    @Override
    public LeaveApplication applyForLeaveRequest(com.example.leave_application.DTO.User.LeaveApplicationDTO leaveRequest) {
        LeaveApplication leaveApplication= new LeaveApplication(
                leaveRequest.getFromDate(),
                leaveRequest.getToDate(),
                leaveRequest.getRemark(),
                leaveRequest.getUser(),
                leaveRequest.getLeaveType()
                );
        leaveApplication.setLeaveStatus(LeaveStatus.PENDING);

        YearlyLeave yearlyLeave=yearlyLeaveService.findYearlyLeaveByLeaveTypeLeaveTypeId(leaveApplication.getLeaveType().getLeaveTypeId());

        List<LeaveApplication>leaveApplicationList=leaveApplicationRepository.findLeaveApplicationByUserUserId(leaveApplication.getUser().getUserId());

        int sumOfTotalLeave=0;
        for(LeaveApplication application: leaveApplicationList){
            if(application.getLeaveStatus()==LeaveStatus.APPROVED){
                int duration=application.getToDate().getDate()-application.getFromDate().getDate();
                sumOfTotalLeave+=duration;
            }
        }
        int maximumDay=yearlyLeave.getMaximumDay();

        Date date=new Date();
        int y=date.getYear();
        int currentYear=y+1900;
        int year=yearlyLeave.getYear();
        int leaveBalance=maximumDay-sumOfTotalLeave;

        if(leaveBalance>0 && year==currentYear){
            return leaveApplicationRepository.save(leaveApplication);
        }
        else{
            throw new BalanceNotAvailableException("Leave Balance not available!!");
        }
    }

    @Override
    public LeaveApplication updateLeaveApplication(Long id, LeaveApplication leaveApplication) {

        if(leaveApplicationRepository.findLeaveApplicationById(id)!=null){
            leaveApplication.setId(id);
            return leaveApplicationRepository.save(leaveApplication);
        }
        throw new LeaveApplicationNotFoundException("Leave application not found!!");
    }

    @Override
    public List<LeaveApplication> findLeaveApplicationByDateRange(Date fromDate, Date toDate) {
        return leaveApplicationRepository.findLeaveApplicationByFromDateBetween(fromDate, toDate);
    }

    @Override
    public List<LeaveApplication>findLeaveApplicationByLeaveStatus(LeaveStatus leaveStatus) {
        if(leaveStatus!=null){
            leaveApplicationRepository.findAllByLeaveStatus(leaveStatus);
        }
        return null;
    }

    @Override
    public LeaveApplication findLeaveApplicationById(Long id) {
        return leaveApplicationRepository.findLeaveApplicationById(id);
    }

    @Override
    public List<LeaveApplication> findLeaveApplicationByUserUserId(Long userId) {
        return leaveApplicationRepository.findLeaveApplicationByUserUserId(userId);
    }

    @Override
    public List<LeaveApplication> findLeaveApplicationByUserUserIdAndLeaveStatus(Long userId, LeaveStatus leaveStatus) {
        return leaveApplicationRepository.findLeaveApplicationByUserUserIdAndLeaveStatus(userId, leaveStatus);
    }

    @Override
    public int showLeaveBalance(Long userId) {
        LeaveApplication leaveApplication=leaveApplicationRepository.findAllByUserUserId(userId);

        YearlyLeave yearlyLeave=yearlyLeaveService.findYearlyLeaveByLeaveTypeLeaveTypeId(leaveApplication.getLeaveType().getLeaveTypeId());


        List<LeaveApplication>leaveApplicationList=leaveApplicationRepository.findLeaveApplicationByUserUserId(userId);


        int sumOfTotalLeave=0;
        for(LeaveApplication application: leaveApplicationList){
            if(application.getLeaveStatus()==LeaveStatus.APPROVED){
                int duration=application.getToDate().getDate()-application.getFromDate().getDate();
                sumOfTotalLeave+=duration;
            }
        }
        int maximumDay=yearlyLeave.getMaximumDay();
        Date date=new Date();
        int y=date.getYear();
        int currentYear=y+1900;
        int year=yearlyLeave.getYear();
        int leaveBalance;
        if(year==currentYear){
            leaveBalance=maximumDay-sumOfTotalLeave;
        }
        else {
            leaveBalance=0;
        }

        if(leaveApplication!=null){
            return leaveBalance;
        }
        else {
            throw new LeaveApplicationNotFoundException("Leave Application not found!!");
        }
    }

    @Override
    public LeaveApplication findAllByUserUserId(Long userId) {
        return leaveApplicationRepository.findAllByUserUserId(userId);
    }

    @Override
    public int countLeaveApplicationsByUserUserId(Long userId) {
        return leaveApplicationRepository.countLeaveApplicationsByUserUserId(userId);
    }
}
