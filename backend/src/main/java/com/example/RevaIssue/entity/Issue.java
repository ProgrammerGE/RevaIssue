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
    private Long issueID;

    @Column(name = "issue_description")
    private String description;

    @ManyToOne(optional = false)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;
    // TODO: since these are exhaustive, we should consider using enums. Same for severity and status
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