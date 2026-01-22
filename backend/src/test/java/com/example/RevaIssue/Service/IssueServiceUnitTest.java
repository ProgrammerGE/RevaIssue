package com.example.RevaIssue.Service;

import com.example.RevaIssue.entity.Issue;
import com.example.RevaIssue.repository.IssueRepository;
import com.example.RevaIssue.service.IssueService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class IssueServiceUnitTest {

    @Mock
    private IssueRepository issueRepo;

    @InjectMocks
    private IssueService issueService;

    private Issue mockIssue;

    @BeforeEach
    void setUp() {
        mockIssue = new Issue();
        mockIssue.setIssueID(1);
        mockIssue.setName("Test Issue");
        mockIssue.setStatus("OPEN");
        mockIssue.setSeverity(1);
        mockIssue.setPriority(1);
    }

    @Test
    void createIssue_PositiveTest() {
        when(issueRepo.save(any(Issue.class))).thenAnswer(i -> i.getArguments()[0]);

        Issue result = issueService.createIssue(mockIssue);

        assertNotNull(result);
        assertEquals("Test Issue", result.getName());
        verify(issueRepo, times(1)).save(any(Issue.class));
    }

    @Test
    void getIssue_PositiveTest() {
        when(issueRepo.findById(1L)).thenReturn(Optional.of(mockIssue));

        Issue result = issueService.getIssue(1L);

        assertNotNull(result);
        assertEquals(1, result.getIssueID());
    }

    @Test
    void getIssue_NotFoundTest() {
        when(issueRepo.findById(1L)).thenReturn(Optional.empty());

        Issue result = issueService.getIssue(1L);

        assertNull(result);
    }

    @Test
    void getMostRecentIssues_PositiveTest() {
        List<Issue> issues = Arrays.asList(mockIssue, new Issue(), new Issue());
        when(issueRepo.findTop5ByOrderByDateCreatedDesc()).thenReturn(issues);

        List<Issue> result = issueService.getMostRecentIssues();

        assertEquals(3, result.size());
        verify(issueRepo, times(1)).findTop5ByOrderByDateCreatedDesc();
    }

    @Test
    void getIssuesByProject_PositiveTest() {
        List<Issue> projectIssues = Collections.singletonList(mockIssue);
        when(issueRepo.findByProjectProjectID(10L)).thenReturn(projectIssues);

        List<Issue> result = issueService.getIssuesByProject(10L);

        assertEquals(1, result.size());
        verify(issueRepo, times(1)).findByProjectProjectID(10L);
    }

    @Test
    void getIssuesByFilter_PositiveTest() {
        List<Issue> filteredIssues = Collections.singletonList(mockIssue);
        when(issueRepo.findByFilter("OPEN", 1, 1)).thenReturn(filteredIssues);

        List<Issue> result = issueService.getIssuesByFilter("OPEN", 1, 1);

        assertFalse(result.isEmpty());
        verify(issueRepo, times(1)).findByFilter("OPEN", 1, 1);
    }

    @Test
    void updateIssue_PositiveTest() {
        Issue updates = new Issue();
        updates.setName("Updated Name");
        updates.setDescription("Updated Desc");

        when(issueRepo.findById(1L)).thenReturn(Optional.of(mockIssue));
        when(issueRepo.save(any(Issue.class))).thenAnswer(i -> i.getArguments()[0]);

        Issue result = issueService.updateIssue(1L, updates);

        assertNotNull(result);
        assertEquals("Updated Name", result.getName());
        verify(issueRepo, times(1)).save(any(Issue.class));
    }

    @Test
    void updateIssue_NotFoundTest() {
        when(issueRepo.findById(1L)).thenReturn(Optional.empty());

        Issue result = issueService.updateIssue(1L, new Issue());

        assertNull(result);
        verify(issueRepo, never()).save(any(Issue.class));
    }

    @Test
    void updateIssueStatus_AuthorizedRole_PositiveTest() {
        when(issueRepo.findById(1L)).thenReturn(Optional.of(mockIssue));
        when(issueRepo.save(any(Issue.class))).thenAnswer(i -> i.getArguments()[0]);

        Issue result = issueService.updateIssueStatus(1L, "RESOLVED", "DEVELOPER");

        assertEquals("RESOLVED", result.getStatus());
        verify(issueRepo, times(1)).save(any(Issue.class));
    }

    @Test
    void updateIssueStatus_UnauthorizedRole_NegativeTest() {
        // tester trying to do Developer work
        assertThrows(RuntimeException.class, () -> {
            issueService.updateIssueStatus(1L, "IN_PROGRESS", "TESTER");
        });

        // Developer trying to do Tester work
        assertThrows(RuntimeException.class, () -> {
            issueService.updateIssueStatus(1L, "CLOSED", "DEVELOPER");
        });

        verify(issueRepo, never()).save(any(Issue.class));
    }

    @Test
    void getIssuesByKeyword_PositiveTest() {
        when(issueRepo.findByKeyword("Bug")).thenReturn(Collections.singletonList(mockIssue));

        List<Issue> result = issueService.getIssuesByKeyword("Bug");

        assertFalse(result.isEmpty());
        verify(issueRepo, times(1)).findByKeyword("Bug");
    }
}