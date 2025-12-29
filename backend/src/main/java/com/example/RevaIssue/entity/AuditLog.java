package com.example.RevaIssue.entity;

import jakarta.persistence.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "audit_logs")
public class AuditLog {
    /**
     * This entity will help log any changed to the databases
     * for auditing purposes. The audit actions will focus on
     * CREATION, UPDATING, and DELETING actions in any entity
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID logID;
    private LocalDateTime timeLogged;
    private String action;
    private String entityId;

    public AuditLog(String action, String entityId){
        this.logID = UUID.randomUUID();
        this.timeLogged = LocalDateTime.now();
        this.action = action;
        this.entityId = entityId;
    }

    public String toString(){
        return this.action + " in the " + entityId + " entity at " + timeLogged;
    }
}
