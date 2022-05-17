package com.example.leave_application.repository;

import com.example.leave_application.entity.LeaveApplication;
import com.example.leave_application.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByEmail(String email);
    List<User> findUserByRolesRoleName(String roleName);
    List<User> findUserByManagerId(Long managerId);

    Optional<User> findByAccessToken(String token);
}
