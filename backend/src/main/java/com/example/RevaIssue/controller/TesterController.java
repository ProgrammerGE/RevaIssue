package com.example.RevaIssue.controller;

import com.example.RevaIssue.entity.AuditLog;
import com.example.RevaIssue.entity.Issue;
import com.example.RevaIssue.entity.Project;
import com.example.RevaIssue.service.*;
import com.example.RevaIssue.util.JwtUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/tester")
public class TesterController {
    /*
    Eric's example controller imports and uses repositories, but since we are using services, they should be imported
    in the service classes that use them.
     */
    // One or some of these may not be necessary TODO : Remove if not necessary after MVP
    @Autowired
    private JwtUtility jwtUtility;
    @Autowired
    private IssueService issueService;
    @Autowired
    private UserService userService;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private AuditLogService auditLogService;
    @Autowired
    private AuthService authService;


    @PostMapping("/project/{project_id}/issues")
    public Issue createIssue(@PathVariable("project_id") int projectId, @RequestBody Issue issue, @RequestHeader (name = "Authorization") String authHeader){
        String role = authService.getRoleFromHeader(authHeader);
        String username = authService.getUsernameFromHeader(authHeader);
        Project project = projectService.getProjectById(projectId);
        issue.setProject(project);
        AuditLog auditLog = auditLogService.createAuditLog(new AuditLog("CREATED ISSUE " + issue.getName(), username, role));
        return issueService.createIssue(issue);
    }

    @PatchMapping("/project/issues/{issue_id}/close")
    public Issue closeIssue(@PathVariable("issue_id") Long issueId, @RequestHeader (name = "Authorization") String authHeader){
        String role = authService.getRoleFromHeader(authHeader);
        String username = authService.getUsernameFromHeader(authHeader);
        String issueName = issueService.getIssue(issueId).getName();
        if(!role.equalsIgnoreCase("tester")){
            return null;
        }
        AuditLog auditLog = auditLogService.createAuditLog(new AuditLog("CLOSED ISSUE " + issueName, username, role));
        return issueService.updateIssueStatus(issueId, "CLOSED", role);
    }

    @PatchMapping("/project/issues/{issue_id}/open")
    public Issue reopenIssue(@PathVariable("issue_id") Long issueId, @RequestHeader (name = "Authorization") String authHeader){
        String role = authService.getRoleFromHeader(authHeader);
        String username = authService.getUsernameFromHeader(authHeader);
        String issueName = issueService.getIssue(issueId).getName();
        if(!role.equalsIgnoreCase("tester")){
            return null;
        }
        AuditLog auditLog = auditLogService.createAuditLog(new AuditLog("OPENED ISSUE " + issueName, username, role));
        return issueService.updateIssueStatus(issueId, "OPEN", role);
    }
    @GetMapping("/project/{project_id}/issues")
    public List<Issue> issueList(@PathVariable("project_id") Long projectId){
        return issueService.getIssuesByProject(projectId);
    }
}
