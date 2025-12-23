package com.example.RevaIssue.controller;

import com.example.RevaIssue.repository.IssueRepository;
import com.example.RevaIssue.repository.ProjectRepository;
import com.example.RevaIssue.repository.UserRepository;
import com.example.RevaIssue.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeveloperController {
    // One or some of these may not be necessary TODO : Remove if not necessary after MVP
    // TODO : Create other services and import them, before uncommenting
//    @Autowired
//    private IssueService issueService;
    @Autowired
    private UserService userService;
//    @Autowired
//    private ProjectService projectService;

}
