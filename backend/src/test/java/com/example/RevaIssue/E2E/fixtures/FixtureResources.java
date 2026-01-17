package com.example.RevaIssue.E2E.fixtures;

import com.example.RevaIssue.E2E.driver.ChromeDriverManager;
import com.example.RevaIssue.E2E.poms.HubPage;
import com.example.RevaIssue.E2E.poms.ProjectPage;
import com.example.RevaIssue.E2E.poms.RegisterPage;
import com.example.RevaIssue.entity.Issue;
import com.example.RevaIssue.entity.Project;
import com.example.RevaIssue.entity.User;
import com.example.RevaIssue.repository.IssueRepository;
import com.example.RevaIssue.repository.ProjectRepository;
import com.example.RevaIssue.repository.UserRepository;
import com.example.RevaIssue.service.UserService;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static com.example.RevaIssue.enums.UserRole.*;

public class FixtureResources {
    public static HubPage hubpage;
    public static RegisterPage registerPage;
    public static ProjectPage projectPage;

    @Autowired
    ChromeDriverManager driverManager;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private IssueRepository issueRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private UserService userService;


    @Before
    public void setup(){
        cleanDatabase();
        createProjectWithIssue();
        hubpage = new HubPage(driverManager);
        registerPage = new RegisterPage(driverManager);
        projectPage = new ProjectPage(driverManager);
    }

    public void createProjectWithIssue() {
        createUsers();
        Project project = new Project();
        project.setProjectName("Test Project");
        project.setProjectDescription("Test Project Description");
        Project savedProject = projectRepository.saveAndFlush(project);
        Issue issue = new Issue();
        issue.setProject(savedProject);
        issue.setDateCreated(LocalDateTime.now());
        issue.setName("First Issue");
        issue.setPriority(2);
        issue.setSeverity(2);
        issue.setDescription("First Issue Description");
        issue.setStatus("OPEN");
        issueRepository.saveAndFlush(issue);
    }

    public void cleanDatabase() {
        issueRepository.deleteAllInBatch();
        projectRepository.deleteAllInBatch();
        userRepository.deleteAllInBatch();
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

    @After
    public void teardownDriver() {
        driverManager.quit();
    }
}
