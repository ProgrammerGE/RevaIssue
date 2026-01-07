package com.example.RevaIssue.controller;

import com.example.RevaIssue.entity.Issue;
import com.example.RevaIssue.entity.Project;
import com.example.RevaIssue.entity.User;
import com.example.RevaIssue.service.AuditLogService;
import com.example.RevaIssue.service.IssueService;
import com.example.RevaIssue.service.ProjectService;
import com.example.RevaIssue.service.UserService;
import com.example.RevaIssue.util.JwtUtility;
import com.example.RevaIssue.util.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public List<UserDTO> getUsers(@PathVariable int id){
        return userService.getAllUsersByProjectId(id);
    }

    // TODO: Implement. Basically, return a list of every issue with the project id as a key.
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
}
