package com.example.leave_application.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUp {
    private Long userId;
    private String email;
    private String password;
    private String userName;
    private Long managerId;
}
