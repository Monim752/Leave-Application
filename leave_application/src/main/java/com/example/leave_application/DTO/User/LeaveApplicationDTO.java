package com.example.leave_application.DTO.User;

import com.example.leave_application.entity.LeaveType;
import com.example.leave_application.entity.User;
import com.example.leave_application.enums.LeaveStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeaveApplicationDTO {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date fromDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date toDate;
    private String remark;
    private User user;
    private LeaveType leaveType;

}
