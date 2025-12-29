package com.example.RevaIssue.controller;

import com.example.RevaIssue.entity.Issue;
import com.example.RevaIssue.entity.User;
import com.example.RevaIssue.service.IssueService;
import com.example.RevaIssue.service.ProjectService;
import com.example.RevaIssue.service.UserService;
import com.example.RevaIssue.util.JwtUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


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

    // helper method to extract the role from the JWT
    private String getRoleFromHeader(String authHeader){
        String token = authHeader.split(" ")[1];
        return jwtUtility.extractRole(token);
    }

    @PostMapping("/login/tester")
    public String testerLogin(@RequestBody User tester){
        User user = userService.getUserById(tester.getUser_ID());
        return jwtUtility.generateAccessToken(user.getUsername(), user.getUser_Role());
    }

    @PostMapping("/project/{project_id}/issues")
    public Issue createIssue(@RequestBody Issue issue){
        return issueService.createIssue(issue);
    }

    @PatchMapping("/project/{project_id}/issues/{issue_id}/close")
    public Issue closeIssue(@RequestHeader (name = "Authorization") String authHeader, @PathVariable("issue_id") Long issueId){
        String role = getRoleFromHeader(authHeader);
        return issueService.updateIssueStatus(issueId, "CLOSED", role);
    }

    @PatchMapping("/project/{project_id}/issues/{issue_id}/open")
    public Issue reopenIssue(@RequestHeader (name = "Authorization") String authHeader, @PathVariable("issue_id") Long issueId){
        String role = getRoleFromHeader(authHeader);
        return issueService.updateIssueStatus(issueId, "OPEN", role);
    }
}
