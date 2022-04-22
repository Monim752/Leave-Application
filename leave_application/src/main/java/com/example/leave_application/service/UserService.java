package com.example.leave_application.service;

import com.example.leave_application.DTO.SignUp;
import com.example.leave_application.entity.LeaveApplication;
import com.example.leave_application.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {
    User saveData(SignUp signUp);
    List<User> findAllUsers();
    User findUserByEmail(String email);

    void createResetPasswordTokenForUser(User user, String token);

    String validatePasswordResetToken(String token);

    Optional<User> getUserByPasswordResetToken(String token);

    void changePassword(User user, String newPassword);

    boolean checkIfValidOldPassword(User user, String oldPassword);
}
