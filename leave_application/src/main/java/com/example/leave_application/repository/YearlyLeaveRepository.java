package com.example.leave_application.repository;

import com.example.leave_application.entity.YearlyLeave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface YearlyLeaveRepository extends JpaRepository<YearlyLeave, Long> {
    YearlyLeave findYearlyLeaveById(Long id);
    YearlyLeave findYearlyLeaveByMaximumDay(int maximumDay);
    YearlyLeave findYearlyLeaveByLeaveTypeLeaveTypeId(Long leaveTypeId);

}
