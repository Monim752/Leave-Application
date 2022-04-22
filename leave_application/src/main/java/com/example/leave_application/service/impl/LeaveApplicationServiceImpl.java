package com.example.leave_application.service.impl;

import com.example.leave_application.DTO.LeaveApplicationDTO;
import com.example.leave_application.DTO.Manager.ManagerLeaveApplicationDTO;
import com.example.leave_application.entity.LeaveApplication;
import com.example.leave_application.entity.YearlyLeave;
import com.example.leave_application.enums.LeaveStatus;
import com.example.leave_application.exception.BalanceNotAvailableException;
import com.example.leave_application.exception.EmailNotFoundException;
import com.example.leave_application.exception.LeaveApplicationNotFoundException;
import com.example.leave_application.repository.LeaveApplicationRepository;
import com.example.leave_application.repository.YearlyLeaveRepository;
import com.example.leave_application.service.LeaveApplicationService;
import com.example.leave_application.service.YearlyLeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class LeaveApplicationServiceImpl implements LeaveApplicationService {

    @Autowired
    private LeaveApplicationRepository leaveApplicationRepository;
    @Autowired
    private YearlyLeaveRepository yearlyLeaveRepository;
    @Autowired
    private YearlyLeaveService yearlyLeaveService;

    @Override
    public LeaveApplication applyForLeave(LeaveApplication leaveApplication) {

        YearlyLeave yearlyLeave=yearlyLeaveService.findYearlyLeaveByLeaveTypeLeaveTypeId(leaveApplication.getLeaveType().getLeaveTypeId());
        LeaveApplicationDTO leaveApplicationDTO=new LeaveApplicationDTO();

        List<LeaveApplication>leaveApplicationList=leaveApplicationRepository.findByUserUserId(leaveApplication.getUser().getUserId());


        int sumOfTotalLeave=0;
        for(LeaveApplication application: leaveApplicationList){
            if(application.getLeaveStatus()==LeaveStatus.APPROVED){
                int duration=application.getToDate().getDate()-application.getFromDate().getDate();
                sumOfTotalLeave+=duration;
            }
        }
        int maximumDay=yearlyLeave.getMaximumDay();

        int leaveBalance=maximumDay-sumOfTotalLeave;
        leaveApplicationDTO.setLeaveBalance(leaveBalance);
        if(leaveBalance>0){
            return leaveApplicationRepository.save(leaveApplication);
        }
        else{
            throw new BalanceNotAvailableException("Leave Balance not available!!");
        }
    }

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
        LeaveApplicationDTO leaveApplicationDTO=new LeaveApplicationDTO();

        List<LeaveApplication>leaveApplicationList=leaveApplicationRepository.findLeaveApplicationByUserUserId(leaveApplication.getUser().getUserId());

        int sumOfTotalLeave=0;
        for(LeaveApplication application: leaveApplicationList){
            if(application.getLeaveStatus()==LeaveStatus.valueOf("APPROVED")){
                int duration=application.getToDate().getDate()-application.getFromDate().getDate();
                sumOfTotalLeave+=duration;
            }
        }
        int maximumDay=yearlyLeave.getMaximumDay();

        int leaveBalance=maximumDay-sumOfTotalLeave;
        leaveApplicationDTO.setLeaveBalance(leaveBalance);
        if(leaveBalance>0){
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
    public List<LeaveApplication>findLeaveApplicationByLeaveStatus(LeaveStatus leaveStatus) {
        if(leaveStatus!=null){
            leaveApplicationRepository.findLeaveApplicationByLeaveStatus(leaveStatus);
        }
        return null;
    }

    @Override
    public List<LeaveApplication> findByUserUserId(Long userId) {
        return leaveApplicationRepository.findByUserUserId(userId);
    }

    @Override
    public List<LeaveApplication> findLeaveApplicationByUserUserId(Long userId) {
        return leaveApplicationRepository.findLeaveApplicationByUserUserId(userId);
    }

    @Override
    public int countLeaveApplicationsByUserUserId(Long userId) {
        return leaveApplicationRepository.countLeaveApplicationsByUserUserId(userId);
    }
}
