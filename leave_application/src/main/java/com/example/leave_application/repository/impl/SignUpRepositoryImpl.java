package com.example.leave_application.repository.impl;

import com.example.leave_application.DTO.SignUp;
import com.example.leave_application.entity.User;
import com.example.leave_application.repository.SignUpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class SignUpRepositoryImpl implements SignUpRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public User SaveData(SignUp signUp) {
//        String query1="insert into user_info(user_id, user_email, " +
//                "password, active, roles," +
//                " first_name, " +
//                "last_name) values(?,?,?,?,?,?,?)";
//        jdbcTemplate.update(query,
//                signUp.getUserId(),
//                signUp.getUserEmail(),
//                signUp.getPassword(),
//                signUp.isActive(),
//                signUp.getRoles(),
//                signUp.getFirstName(),
//                signUp.getLastName()
        return null;
    }
}
