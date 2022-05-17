package com.example.leave_application.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Data
@JsonIgnoreProperties({"hibernateLazyInitializer"})
@Entity
@NoArgsConstructor

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @Column(name = "email",unique = true, nullable = false, length = 100)
    private String email;
    @Column(name = "password", nullable = false, length = 100)
    private String password;
    private String userName;
    private Long managerId;
    private LocalDateTime tokenExpireTime;
    private String accessToken;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "users_roles",
    joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "userId"),
    inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "roleId"))
    private Collection<Role> roles;


    public User(String email, String password, String userName, Long managerId, Collection<Role> roles) {
        super();
        this.email = email;
        this.password = password;
        this.userName = userName;
        this.roles = roles;
        this.managerId=managerId;
    }

    public User(Long userId) {
        super();
        this.userId=userId;
    }

    public User(String email, String password, String userName, Collection<Role> roles) {
        super();
        this.email=email;
        this.password=password;
        this.userName=userName;
        this.roles=roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        return userId != null && userId.equals(((User) o).getUserId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
