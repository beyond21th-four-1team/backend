package com.mac4.yeopabackend.user.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "users",uniqueConstraints = @UniqueConstraint(name = "UK_USER_EMAIL", columnNames = {"email"}))
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 이메일
    @Column(unique = true, nullable = false)
    private String email;

    // 비밀번호
    @Column(nullable = false)
    private String password;

    // 사용자 이름
    @Column(nullable = false)
    private String username;

    // 한줄 소개
    @Column(nullable = true)
    private String description;

    @Builder
    private User(String email, String password, String username, String description) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.description = description;
    }

    public void modifyDescription(String description) {
        this.description = description;
    }
}
