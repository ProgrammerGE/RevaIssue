package com.example.RevaIssue.controller;

import com.example.RevaIssue.entity.AuditLog;
import com.example.RevaIssue.entity.Issue;
import com.example.RevaIssue.service.AuditLogService;
import com.example.RevaIssue.service.IssueService;
import com.example.RevaIssue.service.ProjectService;
import com.example.RevaIssue.service.UserService;
import com.example.RevaIssue.util.JwtUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/developer")
public class DeveloperController {
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

    private String getRoleFromHeader(String authHeader){
        String token = authHeader.split(" ")[1];
        return jwtUtility.extractRole(token);
    }

    private String getUsernameFromHeader(String authHeader){
        String token = authHeader.split(" ")[1];
        return jwtUtility.extractUsername(token);
    }

    @PatchMapping("/project/issues/{issue_id}/in-progress")
    public ResponseEntity<Issue>  moveToInProgress(
            @RequestHeader (name = "Authorization") String authHeader,
            @PathVariable("issue_id") Long issueId
    ) {
        String role = getRoleFromHeader(authHeader).toLowerCase();
        String username = getUsernameFromHeader(authHeader);
        String issueName = issueService.getIssue(issueId).getName();
        if(!role.equals("developer")){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        AuditLog auditLog = auditLogService.createAuditLog(new AuditLog("MOVED ISSUE " + issueName + " TO IN_PROGRESS", username, role));
        return ResponseEntity.ok(issueService.updateIssueStatus(issueId, "IN_PROGRESS", role));
    }

    @PatchMapping("/project/issues/{issue_id}/resolve")
    public ResponseEntity<Issue> resolveIssue(
            @RequestHeader (name = "Authorization") String authHeader,
            @PathVariable("issue_id") Long issueId
    ) {
        String role = getRoleFromHeader(authHeader).toLowerCase();
        String username = getUsernameFromHeader(authHeader);
        String issueName = issueService.getIssue(issueId).getName();
        if(!role.equals("developer")){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        AuditLog auditLog = auditLogService.createAuditLog(new AuditLog("MOVED ISSUE " + issueName + " TO RESOLVED", username, role));
        return ResponseEntity.ok(issueService.updateIssueStatus(issueId, "RESOLVED", role));
    }
    @GetMapping("/project/{project_id}/issues")
    public List<Issue> issueList(@PathVariable("project_id") Long projectId){
               return issueService.getIssuesByProject(projectId);
    }
}
