package com.example.myweb.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 50)
    private String email;

    @Column(nullable = false, length = 10)
    private String nickname;

    private String profileImage;

    private String provider;

    private String providerId;

    @Column(name = "activated")
    private boolean activated;

    @Column(nullable = false, length = 10)
    @Enumerated(value = EnumType.STRING) // String값 자체를 저장
    private Role role;

    @CreationTimestamp
    private Timestamp createDate;

    public enum Role {
        ROLE_USER, ROLE_MANAGER, ROLE_ADMIN
    }

    public User(String username, String password, String email, String nickname, String profileImage, String provider, String providerId, Role role, Timestamp createDate) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.nickname = nickname;
        this.profileImage = profileImage;
        this.provider = provider;
        this.providerId = providerId;
        this.role = role;
        this.createDate = createDate;
    }
}
