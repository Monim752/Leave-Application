package com.example.leave_application.controller;

import com.example.leave_application.DTO.ChangePassword;
import com.example.leave_application.DTO.ResetPasswordDTO;
import com.example.leave_application.entity.User;
import com.example.leave_application.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Optional;
import java.util.UUID;

@RestController
@Slf4j
public class ChangePasswordController {
    @Autowired
    private UserService userService;

    @PostMapping("/resetPassword")
    public String changePassword(@RequestBody ResetPasswordDTO resetPasswordDTO, HttpServletRequest request){
        User user=userService.findUserByEmail(resetPasswordDTO.getEmail());
        String url="";

        if(user!=null){
            String token= UUID.randomUUID().toString();
            userService.createResetPasswordTokenForUser(user, token);
            url=passwordResetTokenMail(user, applicationUrl(request), token);
        }
        return url;
    }

    @PostMapping("/savePassword")
    public String savePassword(@RequestParam("token") String token,
                               @RequestBody ResetPasswordDTO resetPasswordDTO) {
        String result = userService.validatePasswordResetToken(token);
        if(!result.equalsIgnoreCase("valid")) {
            return "Invalid Token";
        }
        Optional<User> user = userService.getUserByPasswordResetToken(token);
        if(user.isPresent()) {
            userService.changePassword(user.get(), resetPasswordDTO.getNewPassword());
            return "Password Reset Successfully";
        } else {
            return "Invalid Token";
        }
    }

    @PostMapping("/changePassword")
    public String changePassword(@RequestBody ResetPasswordDTO resetPasswordDTO){
        User user = userService.findUserByEmail(resetPasswordDTO.getEmail());
        if(!userService.checkIfValidOldPassword(user,resetPasswordDTO.getOldPassword())) {
            return "Invalid Old Password";
        }

        //Save New Password
        userService.changePassword(user,resetPasswordDTO.getNewPassword());
        return "Password Changed Successfully";
    }

    private String passwordResetTokenMail(User user, String applicationUrl, String token) {
        String url =
                applicationUrl
                        + "/savePassword?token="
                        + token;

        //sendVerificationEmail()

        log.info("Click the link to Reset your Password: {}", url);
        return url;
    }

    private String applicationUrl(HttpServletRequest request) {
        return "http://" +
                request.getServerName() +
                ":" +
                request.getServerPort() +
                request.getContextPath();
    }
}
