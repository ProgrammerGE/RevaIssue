package com.example.RevaIssue.helper;

import com.example.RevaIssue.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.example.RevaIssue.entity.Issue;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * This class is to represent the Comment Chain for the Issue Entity
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "comments")
public class Comment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long comment_id;

    @Column(nullable = false, length = 2000)
    private String text;

    @Column(name = "created_at")
    private LocalDateTime timeLogged;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "issue_id", nullable = false)
    @JsonIgnore
    private Issue issue;




}
