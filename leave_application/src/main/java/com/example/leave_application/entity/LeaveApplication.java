package com.example.leave_application.entity;

import com.example.leave_application.enums.LeaveStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;


@Data
@JsonIgnoreProperties({"hibernateLazyInitializer"})
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class LeaveApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date fromDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date toDate;
//    @Enumerated(EnumType.STRING)
    private LeaveStatus leaveStatus;
    private String remark;
    private String managerRemark;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    private User user;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "leave_type_id", referencedColumnName = "leaveTypeId")
    private LeaveType leaveType;

    public LeaveApplication(Date fromDate, Date toDate, String remark, User user, LeaveType leaveType){
        super();
        this.fromDate=fromDate;
        this.toDate=toDate;
        this.remark=remark;
        this.user=user;
        this.leaveType=leaveType;
    }
    public LeaveApplication(Long id, LeaveStatus leaveStatus){
        super();
        this.id=id;
        this.leaveStatus=leaveStatus;
    }
}
