package com.example.leave_application.DTO.Manager;

import com.example.leave_application.enums.LeaveStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ManagerLeaveApplicationDTO {
    private Long id;
    private LeaveStatus leaveStatus;
    private int leaveBalance;
}
