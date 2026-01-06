package com.example.RevaIssue.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@Entity
@Table(name = "Projects")
public class Project {
    

    @Id
    @Column(name = "project_ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int projectID;
    @Column(name = "Project_Name", nullable = false)
    private String projectName;
    @Column(name = "Project_Description", nullable = true)
    private String projectDescription;
}
