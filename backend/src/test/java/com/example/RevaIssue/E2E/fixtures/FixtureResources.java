package com.example.RevaIssue.E2E.fixtures;

import com.example.RevaIssue.E2E.poms.HubPage;
import com.example.RevaIssue.E2E.poms.ProjectPage;
import com.example.RevaIssue.E2E.poms.RegisterPage;
import com.example.RevaIssue.entity.Issue;
import com.example.RevaIssue.entity.Project;
import com.example.RevaIssue.entity.User;
import com.example.RevaIssue.repository.IssueRepository;
import com.example.RevaIssue.repository.ProjectRepository;
import com.example.RevaIssue.repository.UserRepository;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

import static com.example.RevaIssue.enums.UserRole.ADMIN;

public class FixtureResources {
    public static WebDriver driver;
    public static HubPage hubpage;
    public static RegisterPage registerPage;
    public static ProjectPage projectPage;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private IssueRepository issueRepository;

    @Autowired
    UserRepository userRepository;


    @Before
    public void setup(){
        cleanDatabase();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        hubpage = new HubPage(driver);
        registerPage = new RegisterPage(driver);
        projectPage = new ProjectPage(driver);

        createUsers();
        createProjectWithIssue();
    }

    public void createProjectWithIssue() {
        Project project = new Project();
        project.setProjectName("Test Project");
        project.setProjectDescription("Test Project Description");
        Project savedProject = projectRepository.save(project);

        Issue issue = new Issue();
        issue.setProject(savedProject);
        issue.setDateCreated(LocalDateTime.now());
        issue.setName("First Issue");
        issue.setPriority(2);
        issue.setSeverity(2);
        issue.setDescription("First Issue Description");
        issue.setStatus("OPEN");
        issueRepository.save(issue);
    }

    public void createUsers(){
        createAdmin();
    // will need to create tester and dev for user specific testing
    }

    public void createAdmin(){
        User user = new User();
        user.setUserRole(ADMIN);
        user.setUsername("admin");
        user.setPassword("admin");
        userRepository.save(user);
    }


    @BeforeEach
    public void cleanDatabase() {
        issueRepository.deleteAll();
        projectRepository.deleteAll();
        userRepository.deleteAll();
    }
    @After
    public static void tearDown(){
        if(driver != null)
            driver.quit();
    }
}
