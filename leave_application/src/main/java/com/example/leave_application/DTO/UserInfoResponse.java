package com.example.leave_application.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoResponse {
    private String username;
    private String email;
    List<String>roles;
}
