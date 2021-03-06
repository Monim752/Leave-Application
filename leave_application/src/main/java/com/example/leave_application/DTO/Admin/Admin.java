package com.example.leave_application.DTO.Admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Admin {
    private Long userId;
    private String email;
    private String password;
    private String userName;
    private String level;
}
