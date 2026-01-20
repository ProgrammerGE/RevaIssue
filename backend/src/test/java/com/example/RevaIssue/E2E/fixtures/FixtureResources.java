package com.example.RevaIssue.E2E.fixtures;

import com.example.RevaIssue.E2E.driver.ChromeDriverManager;
import com.example.RevaIssue.entity.Issue;
import com.example.RevaIssue.entity.Project;
import com.example.RevaIssue.entity.User;
import com.example.RevaIssue.repository.*;
import com.example.RevaIssue.service.UserService;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static com.example.RevaIssue.enums.UserRole.*;

public class FixtureResources {

    @Autowired
    ChromeDriverManager driverManager;

    @Autowired
    private AuditLogRepository auditLogRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private IssueRepository issueRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private User_ProjectsRepository userProjectsRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;


    @Before
    public void setup(){
        cleanDatabase();
        createProjectWithIssues();
    }

    public void createProjectWithIssues() {
        createUsers();
        Project project = new Project();
        project.setProjectName("Test Project");
        project.setProjectDescription("Test Project Description");
        Project savedProject = projectRepository.saveAndFlush(project);
        createMultipleIssues(savedProject);
    }

    public void cleanDatabase() {
        //repos with FKs need to be cleared first to avoid errors
        userProjectsRepository.deleteAllInBatch();
        commentRepository.deleteAllInBatch();
        issueRepository.deleteAllInBatch();
        projectRepository.deleteAllInBatch();
        userRepository.deleteAllInBatch();
        auditLogRepository.deleteAllInBatch();
    }

    public void createUsers() {
        createAdmin();
        createTester();
        createDeveloper();
    }

    private void createAdmin() {
        User admin = new User();
        admin.setUserRole(ADMIN);
        admin.setUsername("admin");
        admin.setPassword("admin");
        userService.createUser(admin);

    } private void createTester() {
        User tester = new User();
        tester.setUserRole(TESTER);
        tester.setUsername("tester");
        tester.setPassword("tester");
        userService.createUser(tester);

    } private void createDeveloper() {
        User dev = new User();
        dev.setUserRole(DEVELOPER);
        dev.setUsername("dev");
        dev.setPassword("dev");
        userService.createUser(dev);

    }

    private void createNewIssue(
                                Project project,
                                String issueName,
                                int priority,
                                int severity,
                                String status
                                ){
            Issue issue = new Issue();
            issue.setProject(project);
            issue.setDateCreated(LocalDateTime.now());
            issue.setName(issueName);
            issue.setPriority(priority);
            issue.setSeverity(severity);
            issue.setDescription("Desc");
            issue.setStatus(status);
            issueRepository.saveAndFlush(issue);
    }

    private void createMultipleIssues(Project project){
        createNewIssue(project, "1ST P1-S1",        1, 1, "OPEN");
        createNewIssue(project, "2ND P1-S1",      1, 1, "CLOSED");
        createNewIssue(project, "3RD P1-S1", 1, 1, "IN_PROGRESS");
        createNewIssue(project, "4TH P1-S1",    1, 1, "RESOLVED");

        createNewIssue(project, "1ST P2-S2",        2, 2, "OPEN");
        createNewIssue(project, "2ND P2-S2",      2, 2, "CLOSED");
        createNewIssue(project, "3RD P2-S2", 2, 2, "IN_PROGRESS");
        createNewIssue(project, "4TH P2-S2",    2, 2, "RESOLVED");

        createNewIssue(project, "1ST P3-S3",        3, 3, "OPEN");
        createNewIssue(project, "2ND P3-S3",      3, 3, "CLOSED");
        createNewIssue(project, "3RD P3-S3", 3, 3, "IN_PROGRESS");
        createNewIssue(project, "4TH P3-S3",    3, 3, "RESOLVED");
    }

    @After
    public void teardownDriver() {
        driverManager.quit();
    }
}
