package com.example.leave_application.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class YearlyLeave {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int year;
    private int maximumDay;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "leave_type_id", referencedColumnName = "leaveTypeId")
    private LeaveType leaveType;

    public YearlyLeave(int year, int maximumLeave, LeaveType leaveType) {
        this.year=year;
        this.maximumDay=maximumLeave;
        Long leaveId=leaveType.getLeaveTypeId();
        this.leaveType=leaveType;
    }
}
