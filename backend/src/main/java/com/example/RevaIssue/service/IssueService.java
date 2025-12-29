package com.example.RevaIssue.service;

import com.example.RevaIssue.entity.AuditLog;
import com.example.RevaIssue.entity.Issue;
import com.example.RevaIssue.helper.Comment;
import com.example.RevaIssue.repository.AuditLogRepository;
import com.example.RevaIssue.repository.IssueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class IssueService {

    @Autowired
    private IssueRepository issueRepo;
    @Autowired
    private AuditLogService auditLogService;

    public Issue createIssue(Issue issue){
        AuditLog auditLog = auditLogService.createAuditLog(new AuditLog("ISSUE CREATED", "Issue"));
        return issueRepo.save(issue);
    }

    public Issue getIssue(Long issueId){
        return issueRepo.getReferenceById(issueId);
    }

    public int getSeverityById(Long issueId) {
        Issue targetIssue = issueRepo.getReferenceById(issueId);
        return targetIssue.getSeverity();
    }

    public int getPriorityById(Long issueId) {
        Issue targetIssue = issueRepo.getReferenceById(issueId);
        return targetIssue.getPriority();
    }

    // issue status as per Epic 3, bullet 1 in the User stories MVP
    public String getProgressById(Long issueId) {
        Issue targetIssue = issueRepo.getReferenceById(issueId);
        return targetIssue.getStatus();
    }

    public List<Issue> getIssuesByProject(Long projectId) {
        // create a list object and assign the data using a call to the repo
        List<Issue> issues = issueRepo.findByProjectProjectID(projectId);
        // return the list
        return issues;
    }

    // TODO: updating the issue's description, comment, severity, and priority, status
    public Issue updateIssueDescription(Long issueId, String desc){
        Issue targetIssue = issueRepo.getReferenceById(issueId);
        if(desc.isEmpty()){
            return null;
        }
        targetIssue.setDescription(desc);
        issueRepo.save(targetIssue);
        AuditLog auditLog = auditLogService.createAuditLog(new AuditLog("DESCRIPTION UPDATED", "Issue"));
        return issueRepo.getReferenceById(issueId);
    }

    public Issue updateIssueSeverity(Long issueId, Integer severity){
        Issue targetIssue = issueRepo.getReferenceById(issueId);
        targetIssue.setSeverity(severity);
        issueRepo.save(targetIssue);
        AuditLog auditLog = auditLogService.createAuditLog(new AuditLog("SEVERITY UPDATED", "Issue"));
        return issueRepo.getReferenceById(issueId);
    }

    public Issue updateIssuePriority(Long issueId, Integer priority){
        Issue targetIssue = issueRepo.getReferenceById(issueId);
        targetIssue.setPriority(priority);
        issueRepo.save(targetIssue);
        AuditLog auditLog = auditLogService.createAuditLog(new AuditLog("UPDATE PRIORITY TO " + priority, "Issue"));
        return issueRepo.getReferenceById(issueId);
    }

    public Issue updateIssueStatus(Long issueId, String status, String role){

        // Developer only status updates
        if (status.equals("IN_PROGRESS") && !role.equals("DEVELOPER")) {
            throw new RuntimeException("Only developers can move issues to In Progress");
        }
        if (status.equals("RESOLVED") && !role.equals("DEVELOPER")) {
            throw new RuntimeException("Only developers can resolve issues");
        }

        // Tester only status updates
        if (status.equals("CLOSED") && !role.equals("TESTER")) {
            throw new RuntimeException("Only testers can close issues");
        }
        if (status.equals("OPEN") && !role.equals("TESTER")) {
            throw new RuntimeException("Only testers can reopen issues");
        }
        Issue targetIssue = issueRepo.getReferenceById(issueId);
        targetIssue.setStatus(status);
        issueRepo.save(targetIssue);
        AuditLog auditLog = auditLogService.createAuditLog(new AuditLog("UPDATE STATUS to " + status, "Issue"));
        return issueRepo.getReferenceById(issueId);
    }

    public Issue updateIssueComment(Long issueId, String comment){
        Issue targetIssue = issueRepo.getReferenceById(issueId);
        targetIssue.getComment().setText(comment);
        issueRepo.save(targetIssue);
        AuditLog auditLog = auditLogService.createAuditLog(new AuditLog("COMMENT UPDATED", "Issue"));
        return targetIssue;
    }

    public Comment createIssueComment(Long issueId, Comment comment){
        Issue targetIssue = issueRepo.getReferenceById(issueId);
        targetIssue.setComment(comment);
        AuditLog auditLog = auditLogService.createAuditLog(new AuditLog("COMMENT CREATED", "Issue"));
        return targetIssue.getComment();
    }

    public Comment getIssueComment(Long issueId){
        return issueRepo.getReferenceById(issueId).getComment();
    }

}
