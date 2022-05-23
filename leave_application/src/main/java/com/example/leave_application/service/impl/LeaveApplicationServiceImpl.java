package com.example.leave_application.service.impl;

import com.example.leave_application.DTO.Manager.ManagerRemarks;
import com.example.leave_application.DTO.User.LeaveApplicationDTO;
import com.example.leave_application.DTO.User.UpdateLeaveApplication;
import com.example.leave_application.entity.LeaveApplication;
import com.example.leave_application.entity.LeaveType;
import com.example.leave_application.entity.User;
import com.example.leave_application.entity.YearlyLeave;
import com.example.leave_application.enums.LeaveStatus;
import com.example.leave_application.exception.BalanceNotAvailableException;
import com.example.leave_application.exception.EmailNotFoundException;
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

import java.util.ArrayList;
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
    public List<LeaveApplication> findAllLeaveApplications() {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String email= authentication.getName();
        User user=userRepository.findUserByEmail(email);
        List<LeaveApplication> leaveApplicationList=leaveApplicationRepository.findAll();
        List<LeaveApplication> applicationList=new ArrayList<>();
        for(LeaveApplication leaveApplication: leaveApplicationList)
        {
            if(leaveApplication.getUser().getManagerId() == user.getUserId()){
                applicationList.add(leaveApplication);
            }
        }
        return applicationList;
    }

    @Override
    public String approveLeaveApplication(Long id, LeaveStatus leaveStatus) {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String email= authentication.getName();
        User user=userRepository.findUserByEmail(email);

        LeaveApplication leaveApplication=leaveApplicationRepository.findLeaveApplicationById(id);
        if(leaveApplication==null){
            return "No leaveApplication found!!";
        }

        if(leaveApplication.getUser().getManagerId()==user.getUserId()){
            if(leaveApplication.getLeaveStatus()==LeaveStatus.APPROVED){
                return "Already Approved!!";
            }
            else {
                leaveApplication.setLeaveStatus(leaveStatus);
                leaveApplicationRepository.save(leaveApplication);
                return "LeaveApplication Approved";
            }
        }
        else {
            return "User Not Found!!";
        }

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
    public String putRemarks(ManagerRemarks managerRemarks, Long id) {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String email= authentication.getName();
        User user=userRepository.findUserByEmail(email);
        LeaveApplication leaveApplication=findLeaveApplicationById(id);
        if(leaveApplication==null){
            return "LeaveApplication not found!!";
        }
        if(leaveApplication.getUser().getManagerId()== user.getUserId()){
            leaveApplication.setManagerRemark(managerRemarks.getRemarks());
            leaveApplicationRepository.save(leaveApplication);
            return "Successfully remarked!!";
        }
        return "User not found!!";
    }

    @Override
    public List<LeaveApplication> findLeaveApplicationByDateRange(Date fromDate, Date toDate) {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String email= authentication.getName();
        User user=userRepository.findUserByEmail(email);
        if(user!=null) {
            return leaveApplicationRepository.findLeaveApplicationByFromDateBetweenAndUserUserId(fromDate, toDate, user.getManagerId());
        }
        return null;
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
    public List<LeaveApplication> showAllPendingLeaves() {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String email= authentication.getName();
        User user=userRepository.findUserByEmail(email);
        List<User>userList=userRepository.findUserByRolesRoleName("MANAGER");
        List<LeaveApplication> leaveApplicationList=new ArrayList<>();

        boolean found=false;
        for (User userInfo:userList){
            if(userInfo.getUserId()==user.getUserId()){
                List<LeaveApplication> leaveApplication=leaveApplicationRepository.findLeaveApplicationByLeaveStatus(LeaveStatus.PENDING);
                for(LeaveApplication application: leaveApplication){
                    if(application.getUser().getManagerId()==user.getUserId()){
                        leaveApplicationList.add(application);
                    }
                }
                found=true;
                break;
            }
        }
        if (found){
            return leaveApplicationList;
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
                break;
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

    @Override
    public int showLeaveBalanceOfUsers(String email, String leaveType) {

        User user=userRepository.findUserByEmail(email);
        List<User>userList=userRepository.findUserByRolesRoleName("MANAGER");

        LeaveType leave=leaveTypeRepository.findLeaveTypeByLeaveTypeName(leaveType);
        List<YearlyLeave> yearlyLeaveList=yearlyLeaveRepository.findYearlyLeaveByLeaveTypeLeaveTypeId(leave.getLeaveTypeId());

        Date date=new Date();
        int y=date.getYear();
        int currentYear=y+1900;

        int maximumDay=0;
        for (YearlyLeave yearlyLeave: yearlyLeaveList){
            if(yearlyLeave.getYear()==currentYear){
                maximumDay=yearlyLeave.getMaximumDay();
                break;
            }
        }

        List<LeaveApplication> leaveApplicationList=leaveApplicationRepository.findLeaveApplicationByLeaveTypeLeaveTypeNameAndUserUserId(leaveType, user.getUserId());

        int sumOfTotalLeave=0;
        boolean found=true;
        for (User userInfo: userList)
        {
            if(userInfo.getUserId()==user.getManagerId()){
                for (LeaveApplication leaveApplication: leaveApplicationList){
                    if(leaveApplication.getLeaveStatus()!=LeaveStatus.PENDING){
                        int duration=leaveApplication.getToDate().getDate()-leaveApplication.getFromDate().getDate();
                        sumOfTotalLeave+=duration;
                    }
                }
                break;
            }
            else {
                found=false;
            }
        }
        int leaveBalance;
        leaveBalance=maximumDay-sumOfTotalLeave;

        if(found){
            return leaveBalance;
        }

        throw new EmailNotFoundException("User not found Exception!!");
    }

    @Override
    public int showLeaveBalanceByLeaveType(String leaveType) {

        LeaveType leave=leaveTypeRepository.findLeaveTypeByLeaveTypeName(leaveType);

        List<YearlyLeave> yearlyLeaveList=yearlyLeaveRepository.findYearlyLeaveByLeaveTypeLeaveTypeId(leave.getLeaveTypeId());

        Date date=new Date();
        int y=date.getYear();
        int currentYear=y+1900;

        int leaveBalance=0;
        for (YearlyLeave yearlyLeave: yearlyLeaveList){
            if(yearlyLeave.getYear()==currentYear){
                leaveBalance=yearlyLeave.getMaximumDay();
                break;
            }
        }
        return leaveBalance;
    }

}
