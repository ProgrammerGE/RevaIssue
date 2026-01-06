package com.example.RevaIssue.entity;

import com.example.RevaIssue.helper.Comment;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "issues")
public class Issue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "issue_id", nullable = false)
    private int issueID;

    @Column(name = "issue_name")
    private String name;

    @Column(name = "issue_description")
    private String description;

    @ManyToOne(optional = false)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;
    @Column(name = "severity")
    private Integer severity;

    @Column(name = "priority")
    private Integer priority;

    //Comment Chain
    @Column(name = "comment_chain")
    private Comment comment;

    @Column(name = "status")
    private String status = "Open"; // "Open", "In Progress", "Resolved", "Close"
}