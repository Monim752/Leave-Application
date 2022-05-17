package com.example.leave_application.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {
    private Long userId;
    private String email;
    private String password;
    private String userName;
    private Long managerId;
    //private Role role;
}
