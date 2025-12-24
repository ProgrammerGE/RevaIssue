package com.example.RevaIssue.controller;

import com.example.RevaIssue.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TesterController {

//    @Autowired
//    private IssueService issueService;
    @Autowired
    private UserService userService;
//    @Autowired
//    private ProjectService projectService;
}
