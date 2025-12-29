package com.example.RevaIssue.controller;

import com.example.RevaIssue.entity.Project;
import com.example.RevaIssue.entity.User;
import com.example.RevaIssue.entity.User_Projects;
import com.example.RevaIssue.repository.IssueRepository;
import com.example.RevaIssue.repository.ProjectRepository;
import com.example.RevaIssue.repository.UserRepository;
import com.example.RevaIssue.service.IssueService;
import com.example.RevaIssue.service.ProjectService;
import com.example.RevaIssue.service.UserService;
import com.example.RevaIssue.util.JwtUtility;
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

    @GetMapping("/projects")
    public List<Project> getProjects(){
        return projectService.getAllProjects();
    }
    @GetMapping("/projects/{id}")
    public Project getProject(@PathVariable int id){
        return projectService.getProjectById(id);
    }
    @PostMapping("/login")
    public String adminLogin(@RequestBody User admin){
        User user = userService.getUserById(admin.getUser_ID());
        return jwtUtility.generateAccessToken(user.getUsername(), user.getUser_Role());
    }

    @PostMapping("/projects/new")
    public Project createProject(@RequestBody Project project){
        return projectService.createProject(project);
    }

    @PostMapping("projects/{projectId}/assign/")
    public User_Projects assignProject(@PathVariable int projectId, @RequestParam UUID uuid){
        return userService.assignProject(projectId, uuid);
    }
}
