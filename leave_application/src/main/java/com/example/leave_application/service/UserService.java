package com.example.leave_application.service;

import com.example.leave_application.DTO.Admin.Admin;
import com.example.leave_application.DTO.Manager.Manager;
import com.example.leave_application.DTO.UserInfo;
import com.example.leave_application.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {
    User addUser(UserInfo signUp);

    User addManager(Manager manager);

    User addAdmin(Admin admin);

    List<User> findAllUsers();

    User findUserByEmail(String email);

    void createResetPasswordTokenForUser(User user, String token);

    String validatePasswordResetToken(String token);

    Optional<User> getUserByPasswordResetToken(String token);

    void changePassword(User user, String password);

    List<User> findUserByRolesRoleName(String roleName);

}
