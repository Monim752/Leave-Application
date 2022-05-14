package com.example.leave_application.repository;

import com.example.leave_application.entity.YearlyLeave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface YearlyLeaveRepository extends JpaRepository<YearlyLeave, Long> {
    YearlyLeave findYearlyLeaveById(Long id);
    YearlyLeave findYearlyLeaveByMaximumDay(int maximumDay);
    //YearlyLeave findYearlyLeaveByLeaveTypeLeaveTypeId(Long leaveTypeId);
    YearlyLeave findYearlyLeaveByLeaveTypeLeaveTypeName(String leaveType);
    List<YearlyLeave> findYearlyLeaveByLeaveTypeLeaveTypeId(Long leaveTypeId);
    YearlyLeave findYearlyLeaveByYear(YearlyLeave year);

}
