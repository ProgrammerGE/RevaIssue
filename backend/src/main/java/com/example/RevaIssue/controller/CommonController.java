package com.example.RevaIssue.controller;

import com.example.RevaIssue.dto.RegisterRequest;
import com.example.RevaIssue.entity.AuditLog;
import com.example.RevaIssue.entity.Issue;
import com.example.RevaIssue.entity.Project;
import com.example.RevaIssue.entity.User;
import com.example.RevaIssue.service.*;
import com.example.RevaIssue.util.JwtUtility;
import com.example.RevaIssue.util.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/common")
public class CommonController {
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

    /*
    The following 'get/post/etc requests' should be available to all user types:
    @ Projects:
    @ Get all the projects that the requesting user is associated with (getProjectsByUserId)
    @ One project the user is associated with (get by id)
    @
    @ Issues:
    @ All Issues from a given project the user is associated with
    @ One issue from a given project the user is associated with
    @
    @ Users:
    @ All users who are in the same projects as the requesting user
     */

    // TODO: Modify. Make it so that the only projects returned are those that are associated with the user
    @GetMapping("/projects")
    public List<Project> getProjects(){
        return projectService.getAllProjects();
    }

    @GetMapping("/projects/{id}")
    public Project getProject(@PathVariable int id){
        return projectService.getProjectById(id);
    }

    // TODO: Implement. Make it return all users associated with a given project.
    @GetMapping("/projects/{id}/users")
    public List<UserDTO> getUsersByProjectId(@PathVariable int id){
        return userService.getAllUsersByProjectId(id);
    }
    
    @GetMapping("/issues/{projectId}")
    public List<Issue> getIssues(@PathVariable int projectId){ return null; }

    // TODO: Implement. Return an issue by its id. Ensure that the user trying to access
    // the issue is allowed to. (Are they associated with the project the issue is associated with?)
    @GetMapping("/issues/")
    public Issue getIssueById(int id){ return null; }

    @GetMapping("/issues/latest")
    public List<Issue> getMostRecentIssues() {
        return issueService.getMostRecentIssues();
    }

    @GetMapping("/issues/{keyword}")
    public List<Issue> getIssuesByKeyword(@PathVariable String keyword){
        return issueService.getIssuesByKeyword(keyword);
    }

    @GetMapping("/projects/search")
    public List<Project> getProjectByKeyword(@RequestParam String keyword){
        return projectService.getProjectsByKeyword(keyword);
    }

    @PatchMapping("/issues/{issue_id}")
    public Issue updateIssue(@PathVariable("issue_id") Long issueId,
                             @RequestBody Issue issue,
                             @RequestHeader (name = "Authorization") String authHeader
                             ){
        String role = authService.getRoleFromHeader(authHeader);
        String userName = authService.getUsernameFromHeader(authHeader);
        String issueName = issueService.getIssue(issueId).getName();
        AuditLog auditLog = auditLogService.createAuditLog(new AuditLog("UPDATED " + issueName + "DETAILS", userName, role));
        return issueService.updateIssue(issueId, issue);
    }
}
