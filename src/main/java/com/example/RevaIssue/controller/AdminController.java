package com.example.RevaIssue.controller;

import com.example.RevaIssue.entity.Project;
import com.example.RevaIssue.repository.IssueRepository;
import com.example.RevaIssue.repository.ProjectRepository;
import com.example.RevaIssue.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {

    // One of these may not be necessary TODO : Remove if not necessary after MVP
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private IssueRepository issueRepository;

//    Dummy endpoint for example purposes
//    @PostMapping("/admin")
//    public Project addProject(@RequestBody Project project) {
//        return projectRepository.save(project);
//    }



}
