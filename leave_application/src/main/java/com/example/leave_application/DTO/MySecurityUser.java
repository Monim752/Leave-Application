package com.example.leave_application.DTO;

import com.example.leave_application.entity.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class MySecurityUser implements UserDetails {

    private String email;
    private String password;
    private List<Role>roles;

    public MySecurityUser(String email, String password, List<Role> roles) {
        this.email=email;
        this.password=password;
        this.roles=roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        ArrayList<GrantedAuthority> authorities=new ArrayList<>();
//        SimpleGrantedAuthority authority=new SimpleGrantedAuthority(roles.);
//        authorities.add(authority);
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getRoleName())).collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
