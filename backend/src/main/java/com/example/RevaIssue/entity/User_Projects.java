package com.example.RevaIssue.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "User_Projects")
public class User_Projects {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "User_ProjectsID", nullable = false)
//    private int ID;
    private Integer ID; // Changed from ID to id (standard) and int to Integer

    @ManyToOne(optional = false)
    @JoinColumn(name = "UserID")
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ProjectID")
    private Project project;
}
