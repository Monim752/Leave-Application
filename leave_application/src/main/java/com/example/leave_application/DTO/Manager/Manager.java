package com.example.leave_application.DTO.Manager;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Manager {
    private Long userId;
    private String email;
    private String password;
    private String userName;
    private String level;
}
