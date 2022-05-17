package com.example.leave_application.repository;

import com.example.leave_application.DTO.UserInfo;
import com.example.leave_application.entity.User;

public interface SignUpRepository {
    User SaveData(UserInfo signUp);
}
