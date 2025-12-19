package com.example.RevaIssue.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Table(name = "User_Projects")
public class Users_Projects {

    @Id
    @Column(name = "UserProjectsID", nullable = false)
    private int ID;
    @ManyToOne
    @Column(name = "UserID")
    private UUID userID;
    @ManyToOne
    @Column(name = "ProjectID")
    private int projectID;
}
