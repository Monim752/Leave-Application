package com.example.leave_application.service.impl;

import com.example.leave_application.DTO.User.LeaveApplicationDTO;
import com.example.leave_application.DTO.User.UpdateLeaveApplication;
import com.example.leave_application.entity.LeaveApplication;
import com.example.leave_application.entity.LeaveType;
import com.example.leave_application.entity.User;
import com.example.leave_application.entity.YearlyLeave;
import com.example.leave_application.enums.LeaveStatus;
import com.example.leave_application.exception.BalanceNotAvailableException;
import com.example.leave_application.exception.LeaveApplicationNotFoundException;
import com.example.leave_application.repository.LeaveApplicationRepository;
import com.example.leave_application.repository.LeaveTypeRepository;
import com.example.leave_application.repository.UserRepository;
import com.example.leave_application.repository.YearlyLeaveRepository;
import com.example.leave_application.service.LeaveApplicationService;
import com.example.leave_application.service.YearlyLeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class LeaveApplicationServiceImpl implements LeaveApplicationService {

    @Autowired
    private LeaveApplicationRepository leaveApplicationRepository;

    @Autowired
    private YearlyLeaveService yearlyLeaveService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private YearlyLeaveRepository yearlyLeaveRepository;
    @Autowired
    private LeaveTypeRepository leaveTypeRepository;

    @Override
    public LeaveApplication createLeaveApplication(com.example.leave_application.DTO.User.LeaveApplicationDTO leaveRequest, String leaveType) {
        LeaveType leave =leaveTypeRepository.findLeaveTypeByLeaveTypeName(leaveType);
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String email= authentication.getName();
        User user=userRepository.findUserByEmail(email);

        LeaveApplication leaveApplication= new LeaveApplication(
                leaveRequest.getFromDate(),
                leaveRequest.getToDate(),
                leaveRequest.getRemark(),
                new LeaveType(leave.getLeaveTypeId()),
                new User(user.getUserId())
                );
        leaveApplication.setLeaveStatus(LeaveStatus.PENDING);

        List<YearlyLeave> yearlyLeaveList=yearlyLeaveRepository.findYearlyLeaveByLeaveTypeLeaveTypeId(leave.getLeaveTypeId());
        Date date=new Date();
        int y=date.getYear();
        int currentYear=y+1900;

        int maximumDay=0;
        for (YearlyLeave yearlyLeave: yearlyLeaveList){
            if (yearlyLeave.getYear()==currentYear){
                maximumDay=yearlyLeave.getMaximumDay();
                break;
            }
        }

        List<LeaveApplication>leaveApplicationList=leaveApplicationRepository.findLeaveApplicationByLeaveTypeLeaveTypeNameAndUserUserId(leaveType, user.getUserId());

        int sumOfTotalLeave=0;
        for(LeaveApplication application: leaveApplicationList){
            if(application.getLeaveStatus()==LeaveStatus.APPROVED){
                int duration=application.getToDate().getDate()-application.getFromDate().getDate();
                sumOfTotalLeave+=duration;
            }
        }


        int leaveBalance=maximumDay-sumOfTotalLeave;

        if(leaveBalance>0){
            return leaveApplicationRepository.save(leaveApplication);
        }
        else{
            throw new BalanceNotAvailableException("Leave Balance not available!!");
        }
    }

    @Override
    public List<LeaveApplication> findAll() {
        return leaveApplicationRepository.findAll();
    }

    @Override
    public LeaveApplication approveLeaveApplication(Long id, LeaveStatus leaveStatus) {
        LeaveApplication leaveApplication=leaveApplicationRepository.findLeaveApplicationById(id);
        leaveApplication.setLeaveStatus(leaveStatus);
        if(leaveApplication!=null){
            return leaveApplicationRepository.save(leaveApplication);
        }
        throw new LeaveApplicationNotFoundException("Leave application not found!!");
    }

    @Override
    public String updateLeaveApplication(UpdateLeaveApplication updateLeaveApplication, Long id) {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String email= authentication.getName();
        User user=userRepository.findUserByEmail(email);

        LeaveApplication leaveApplication=leaveApplicationRepository.findLeaveApplicationByIdAndUserUserId(id, user.getUserId());
        LeaveType leave=leaveTypeRepository.findLeaveTypeByLeaveTypeName(updateLeaveApplication.getLeaveType());

        leaveApplication.setFromDate(updateLeaveApplication.getFromDate());
        leaveApplication.setToDate(updateLeaveApplication.getToDate());
        leaveApplication.setRemark(updateLeaveApplication.getRemark());
        leaveApplication.setLeaveType(leave);

        if(leaveApplication.getLeaveStatus()!=LeaveStatus.APPROVED){
            leaveApplicationRepository.save(leaveApplication);
            return "Leave Application updated!!";
        }
        return "Your Leave Application Already Approved or Rejected!!";
    }

    @Override
    public List<LeaveApplication> findLeaveApplicationByDateRange(Date fromDate, Date toDate) {
        return leaveApplicationRepository.findLeaveApplicationByFromDateBetween(fromDate, toDate);
    }

    @Override
    public List<LeaveApplication> findLeaveApplicationByLeaveTypeLeaveTypeName(String leaveType) {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String email= authentication.getName();
        User user=userRepository.findUserByEmail(email);
        if(leaveType!=null){
            return leaveApplicationRepository.findLeaveApplicationByLeaveTypeLeaveTypeNameAndUserUserId(leaveType, user.getUserId());
        }
        return null;
    }

    @Override
    public List<LeaveApplication>findLeaveApplicationByLeaveStatus(LeaveStatus leaveStatus) {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String email= authentication.getName();
        User user=userRepository.findUserByEmail(email);
        if(leaveStatus!=null){
            return leaveApplicationRepository.findLeaveApplicationByLeaveStatusAndUserUserId(leaveStatus, user.getUserId());
        }
        return null;
    }

    @Override
    public LeaveApplication findLeaveApplicationById(Long id) {
        return leaveApplicationRepository.findLeaveApplicationById(id);
    }

    @Override
    public List<LeaveApplication> findLeaveApplicationByUserUserIdAndLeaveStatus(Long userId, LeaveStatus leaveStatus) {
        return leaveApplicationRepository.findLeaveApplicationByUserUserIdAndLeaveStatus(userId, leaveStatus);
    }

    @Override
    public int showLeaveBalance(String leaveType) {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String email= authentication.getName();
        User user=userRepository.findUserByEmail(email);

        LeaveType leave=leaveTypeRepository.findLeaveTypeByLeaveTypeName(leaveType);
        List<YearlyLeave> yearlyLeaveList=yearlyLeaveRepository.findYearlyLeaveByLeaveTypeLeaveTypeId(leave.getLeaveTypeId());

        Date date=new Date();
        int y=date.getYear();
        int currentYear=y+1900;

        int maximumDay=0;
        for (YearlyLeave yearlyLeave: yearlyLeaveList){
            if(yearlyLeave.getYear()==currentYear){
                maximumDay=yearlyLeave.getMaximumDay();
            }
        }

        List<LeaveApplication>leaveApplicationList=leaveApplicationRepository.findLeaveApplicationByLeaveTypeLeaveTypeNameAndUserUserId(leaveType, user.getUserId());

        int sumOfTotalLeave=0;
        long userId=0;
        for(LeaveApplication application: leaveApplicationList){
            if(application.getLeaveStatus()==LeaveStatus.APPROVED){
                int duration=application.getToDate().getDate()-application.getFromDate().getDate();
                sumOfTotalLeave+=duration;
            }
            userId=application.getUser().getUserId();
        }

        int leaveBalance;

        leaveBalance=maximumDay-sumOfTotalLeave;


        if(userId==user.getUserId()){
            return leaveBalance;
        }
        else {
            throw new LeaveApplicationNotFoundException("Leave Application not found!!");
        }
    }

}
