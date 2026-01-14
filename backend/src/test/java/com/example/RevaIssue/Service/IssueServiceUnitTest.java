package com.example.RevaIssue.Service;

import com.example.RevaIssue.entity.Issue;
import com.example.RevaIssue.entity.Project;
import com.example.RevaIssue.service.IssueService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

public class IssueServiceUnitTest {

    @Mock
    private IssueService issueService;

    @Test
    void createIssueTest(){
        Issue mockIssue = new Issue();
        mockIssue.setIssueID(1);
        mockIssue.setName("Mock Issue");
        mockIssue.setDescription("Mock Description");
        mockIssue.setStatus("Open");
        mockIssue.setSeverity(1);
        mockIssue.setPriority(1);
        mockIssue.setDateCreated(LocalDateTime.now());


        Project mockProject = new Project();
        mockProject.setProjectName("Mock Project");
        mockProject.setProjectDescription("Mock Description");
        mockProject.setProjectID(1);

        mockIssue.setProject(mockProject);

        when(issueService.getIssue((long)1)).thenReturn(mockIssue);

        Issue issue = issueService.createIssue(mockIssue);

        assertNotNull(issue);
        assertEquals(mockIssue, issue);
    }

    @Test
    void getIssueTest(){
        Issue mockIssue = new Issue();
        mockIssue.setIssueID(1);
        mockIssue.setName("Mock Issue");
        mockIssue.setDescription("Mock Description");
        mockIssue.setStatus("Open");
        mockIssue.setSeverity(1);
        mockIssue.setPriority(1);
        mockIssue.setDateCreated(LocalDateTime.now());


        Project mockProject = new Project();
        mockProject.setProjectName("Mock Project");
        mockProject.setProjectDescription("Mock Description");
        mockProject.setProjectID(1);

        mockIssue.setProject(mockProject);

        when(issueService.createIssue(mockIssue)).thenReturn(mockIssue);

        Issue issue = issueService.getIssue((long)1);

        assertNotNull(issue);
        assertEquals(mockIssue, issue);
    }

    @Test
    void getRecentIssuesTest(){
        Issue mockIssue = new Issue();
        mockIssue.setIssueID(1);
        mockIssue.setName("Mock Issue");
        mockIssue.setDescription("Mock Description");
        mockIssue.setStatus("Open");
        mockIssue.setSeverity(1);
        mockIssue.setPriority(1);
        mockIssue.setDateCreated(LocalDateTime.now());


        Project mockProject = new Project();
        mockProject.setProjectName("Mock Project");
        mockProject.setProjectDescription("Mock Description");
        mockProject.setProjectID(1);

        mockIssue.setProject(mockProject);

        List<Issue> mockIssueList = new ArrayList<>();
        mockIssueList.add(mockIssue);

        when(issueService.createIssue(mockIssue)).thenReturn(mockIssue);

        List<Issue> issueList = issueService.getMostRecentIssues();
        assertNotNull(issueList);
        assertEquals(mockIssueList.getFirst(), issueList.getFirst());

    }
    @Test
    void getIssuesByKeywordTest(){
        Issue mockIssue = new Issue();
        mockIssue.setIssueID(1);
        mockIssue.setName("Mock Issue");
        mockIssue.setDescription("Mock Description");
        mockIssue.setStatus("Open");
        mockIssue.setSeverity(1);
        mockIssue.setPriority(1);
        mockIssue.setDateCreated(LocalDateTime.now());


        Project mockProject = new Project();
        mockProject.setProjectName("Mock Project");
        mockProject.setProjectDescription("Mock Description");
        mockProject.setProjectID(1);

        mockIssue.setProject(mockProject);

        List<Issue> mockIssueList = new ArrayList<>();
        mockIssueList.add(mockIssue);

        when(issueService.createIssue(mockIssue)).thenReturn(mockIssue);

        List<Issue> issueList = issueService.getIssuesByKeyword("Mock");
        assertNotNull(issueList);
        assertEquals(mockIssueList.getFirst(), issueList.getFirst());
    }

    @Test
    void updateIssueTest(){
        Issue mockIssue = new Issue();
        mockIssue.setIssueID(1);
        mockIssue.setName("Mock Issue");
        mockIssue.setDescription("Mock Description");
        mockIssue.setStatus("Open");
        mockIssue.setSeverity(1);
        mockIssue.setPriority(1);
        mockIssue.setDateCreated(LocalDateTime.now());


        Project mockProject = new Project();
        mockProject.setProjectName("Mock Project");
        mockProject.setProjectDescription("Mock Description");
        mockProject.setProjectID(1);

        mockIssue.setProject(mockProject);

        Issue mockUpdatedIssue = new Issue();
        mockUpdatedIssue.setIssueID(1);
        mockUpdatedIssue.setName("Updated Issue");
        mockUpdatedIssue.setDescription("Updated Description");
        mockUpdatedIssue.setStatus("Closed");
        mockUpdatedIssue.setSeverity(2);
        mockUpdatedIssue.setPriority(3);
        mockUpdatedIssue.setDateCreated(LocalDateTime.now());
        mockUpdatedIssue.setProject(mockProject);

        when(issueService.createIssue(mockIssue)).thenReturn(mockIssue);

        Issue updatedIssue = issueService.updateIssue((long)1, mockUpdatedIssue);

        assertNotNull(updatedIssue);
        assertEquals(mockUpdatedIssue, updatedIssue);
    }

    /**
     * This unit test will check for the tester's role privilege to change the
     * issue's status from Open to Closed
     */
    @Test
    void updateIssueClosedTesterStatusTest(){
        Project mockProject = new Project();
        mockProject.setProjectName("Mock Project");
        mockProject.setProjectDescription("Mock Description");
        mockProject.setProjectID(1);

        Issue mockIssue = new Issue();
        mockIssue.setIssueID(1);
        mockIssue.setName("Mock Issue");
        mockIssue.setDescription("Mock Description");
        mockIssue.setStatus("Open");
        mockIssue.setSeverity(1);
        mockIssue.setPriority(1);
        mockIssue.setDateCreated(LocalDateTime.now());
        mockIssue.setProject(mockProject);

        Issue mockUpdatedIssue = new Issue();
        mockUpdatedIssue.setIssueID(1);
        mockUpdatedIssue.setName("Mock Issue");
        mockUpdatedIssue.setDescription("Mock Description");
        mockUpdatedIssue.setStatus("Closed");
        mockUpdatedIssue.setSeverity(1);
        mockUpdatedIssue.setPriority(1);
        mockUpdatedIssue.setDateCreated(LocalDateTime.now());
        mockUpdatedIssue.setProject(mockProject);

        when(issueService.createIssue(mockIssue)).thenReturn(mockIssue);

        Issue updatedIssue = issueService.updateIssueStatus((long)1, "CLOSED", "TESTER");
        assertNotNull(updatedIssue);
        assertEquals(mockUpdatedIssue, updatedIssue);
    }

    /**
     * This unit test will check for the developer's role privilege to change the
     * issue's status from In_Progress to Resolved
     */
    @Test
    void updateIssueResolvedDeveloperStatusTest(){
        Project mockProject = new Project();
        mockProject.setProjectName("Mock Project");
        mockProject.setProjectDescription("Mock Description");
        mockProject.setProjectID(1);

        Issue mockIssue = new Issue();
        mockIssue.setIssueID(1);
        mockIssue.setName("Mock Issue");
        mockIssue.setDescription("Mock Description");
        mockIssue.setStatus("Open");
        mockIssue.setSeverity(1);
        mockIssue.setPriority(1);
        mockIssue.setDateCreated(LocalDateTime.now());
        mockIssue.setProject(mockProject);

        Issue mockUpdatedIssue = new Issue();
        mockUpdatedIssue.setIssueID(1);
        mockUpdatedIssue.setName("Mock Issue");
        mockUpdatedIssue.setDescription("Mock Description");
        mockUpdatedIssue.setStatus("Closed");
        mockUpdatedIssue.setSeverity(1);
        mockUpdatedIssue.setPriority(1);
        mockUpdatedIssue.setDateCreated(LocalDateTime.now());
        mockUpdatedIssue.setProject(mockProject);

        when(issueService.createIssue(mockIssue)).thenReturn(mockIssue);

        Issue updatedIssue = issueService.updateIssueStatus((long)1, "RESOLVED", "DEVELOPER");
        assertNotNull(updatedIssue);
        assertEquals(mockUpdatedIssue, updatedIssue);
    }
}
