package com.example.RevaIssue.controller;

import com.example.RevaIssue.entity.AuditLog;
import com.example.RevaIssue.entity.Project;
import com.example.RevaIssue.entity.User;
import com.example.RevaIssue.entity.User_Projects;
import com.example.RevaIssue.repository.IssueRepository;
import com.example.RevaIssue.repository.ProjectRepository;
import com.example.RevaIssue.repository.UserRepository;
import com.example.RevaIssue.service.*;
import com.example.RevaIssue.util.JwtUtility;
import com.example.RevaIssue.util.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.ast.Assign;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
//@CrossOrigin // used for Angular

// feel free to change this mapping to something more suiting if you'd like
@RequestMapping("/admin")
public class AdminController {

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



    @GetMapping("/projects")
    public List<Project> getProjects(){
        return projectService.getAllProjects();
    }

    @GetMapping("/projects/{id}")
    public Project getProject(@PathVariable int id){
        return projectService.getProjectById(id);
    }

    @GetMapping("/audits")
    public List<AuditLog> getAuditLogs(){
        return auditLogService.getAllAuditLogs();
    }

    // Get all users
    @GetMapping("/users")
    public List<UserDTO> getUsers(){ return userService.getAllUsers(); }

    // Assign User to Project by creating a User_Projects entity

    @PostMapping("/projects/new")
    public Project createProject(
            @RequestBody Project project,
            @RequestHeader (name = "Authorization") String authHeader
    ){
        String role = authService.getRoleFromHeader(authHeader);
        String username = authService.getUsernameFromHeader(authHeader);

        if(!role.equalsIgnoreCase("admin")){
            return null;
        }
        AuditLog auditLog = auditLogService.createAuditLog(new AuditLog("CREATED " + project.getProjectName() + " PROJECT", username, role));
        return projectService.createProject(project);
    }

    @PostMapping("projects/{projectId}/assign/{userName}")
    public User_Projects assignProject(@PathVariable int projectId, @PathVariable String userName){
        return userService.assignProject(projectId, userName);
    }
}
