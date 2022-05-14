package com.example.leave_application.DTO;

import com.example.leave_application.entity.LeaveType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class YearlyLeaveDTO {
    private Long id;
    private int year;
    private int maximumDay;
    private LeaveType leaveType;
}
