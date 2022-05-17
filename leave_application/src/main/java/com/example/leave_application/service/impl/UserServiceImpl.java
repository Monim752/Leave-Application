package com.example.leave_application.service.impl;

import com.example.leave_application.DTO.Admin.Admin;
import com.example.leave_application.DTO.Manager.Manager;
import com.example.leave_application.DTO.UserInfo;
import com.example.leave_application.entity.PasswordResetToken;
import com.example.leave_application.entity.Role;
import com.example.leave_application.entity.User;
import com.example.leave_application.exception.EmailNotFoundException;
import com.example.leave_application.repository.PasswordResetTokenRepository;
import com.example.leave_application.repository.UserRepository;
import com.example.leave_application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User addUser(UserInfo signUp) {
        User user=new User(signUp.getEmail(), signUp.getPassword(),
                signUp.getUserName(), signUp.getManagerId(), Arrays.asList(new Role("USER")));
        return userRepository.save(user);
    }

    @Override
    public User addManager(Manager manager) {
        User user=new User(manager.getEmail(), manager.getPassword(),
                manager.getUserName(), Arrays.asList(new Role("MANAGER", manager.getLevel())));
        return userRepository.save(user);
    }

    @Override
    public User addAdmin(Admin admin) {
        User user=new User(admin.getEmail(), admin.getPassword(),
                admin.getUserName(), Arrays.asList(new Role("ADMIN", admin.getLevel())));
        return userRepository.save(user);
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User findUserByEmail(String email) {
        if(email==null){
            throw new EmailNotFoundException("Email not found!!");
        }
        return userRepository.findUserByEmail(email);
    }

    @Override
    public void createResetPasswordTokenForUser(User user, String token) {
        PasswordResetToken passwordResetToken=new PasswordResetToken(user, token);
        passwordResetTokenRepository.save(passwordResetToken);
    }

    @Override
    public String validatePasswordResetToken(String token) {
        PasswordResetToken passwordResetToken
                = passwordResetTokenRepository.findByToken(token);

        if (passwordResetToken == null) {
            return "invalid";
        }

        User user = passwordResetToken.getUser();
        Calendar cal = Calendar.getInstance();

        if ((passwordResetToken.getExpirationTime().getTime()
                - cal.getTime().getTime()) <= 0) {
            passwordResetTokenRepository.delete(passwordResetToken);
            return "expired";
        }

        return "valid";
    }

    @Override
    public Optional<User> getUserByPasswordResetToken(String token) {
        return Optional.ofNullable(passwordResetTokenRepository.findByToken(token).getUser());
    }

    @Override
    public void changePassword(User user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    @Override
    public List<User> findUserByRolesRoleName(String roleName) {
        return userRepository.findUserByRolesRoleName(roleName);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=userRepository.findUserByEmail(username);

        if(user==null){
            throw new UsernameNotFoundException("user not found!!");
        }
        return UserDetailsImpl.build(user);
        //return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

//    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
//        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getRoleName())).collect(Collectors.toList());
//    }
}
