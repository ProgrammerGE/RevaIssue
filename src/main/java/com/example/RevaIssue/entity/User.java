package com.example.RevaIssue.entity;

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
    @Column(name = "User_ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID user_ID;
    @Column
    private String username;
    @Column
    private String password_hash;
    @Column(name = "User_Role", nullable = false)
    private String user_Role;
}
