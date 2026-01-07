package com.example.RevaIssue.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Table(name = "audit_logs")
public class AuditLog {
    /**
     * This entity will help log any changed to the databases
     * for auditing purposes. The audit actions will focus on
     * CREATION, UPDATING, and DELETING actions in any entity
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "audit_id", nullable = false)
    private UUID logID;
    @Column(name = "timestamp")
    private LocalDateTime timeLogged;
    @Column(name = "action")
    private String action;
    @Column(name = "username")
    private String username;  // Who created the log
    @Column(name = "role")
    private String role; // What was there role

    public AuditLog(String action, String userName, String role){
        this.timeLogged = LocalDateTime.now();
        this.action = action;
        this.username = userName;
        this.role = role;
    }

    public String toString(){
        return this.role + ": " + this.username + " " + this.action + " at " + timeLogged;
    }
}
