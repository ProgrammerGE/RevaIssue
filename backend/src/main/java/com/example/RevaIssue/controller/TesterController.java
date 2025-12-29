package com.example.RevaIssue.controller;

import com.example.RevaIssue.entity.User;
import com.example.RevaIssue.service.IssueService;
import com.example.RevaIssue.service.ProjectService;
import com.example.RevaIssue.service.UserService;
import com.example.RevaIssue.util.JwtUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
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

    @PostMapping("/tester")
    public String developerLogin(@RequestBody User admin){
        User user = userService.getUserById(admin.getUser_ID());
        return jwtUtility.generateAccessToken(user.getUsername(), user.getUser_Role());
    }
}
