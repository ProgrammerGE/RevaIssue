package com.example.RevaIssue.entity;

import com.example.RevaIssue.enums.UserRole;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Table(name = "Users")
public class User {

    @Id
    @Column(name = "User_ID")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID user_ID;
    @Column(unique = true, nullable = false)
    private String username;
    @Column(name="password_hash", nullable = false)
    private String password;
    @Enumerated(EnumType.STRING)
    @Column(name = "User_Role", nullable = false)
    private UserRole userRole;
}
