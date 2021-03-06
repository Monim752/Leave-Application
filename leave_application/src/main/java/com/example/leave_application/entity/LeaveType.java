package com.example.leave_application.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeaveType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long leaveTypeId;
    @Column(name = "leaveTypeName", unique = true)
    private String leaveTypeName;
    private String remark;

    public LeaveType(Long leaveTypeId) {
        super();
        this.leaveTypeId=leaveTypeId;
    }

//    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JoinColumn(name = "leave_type_id", referencedColumnName = "leaveTypeId")
//    private List<YearlyLeave> yearlyLeaves;
}
