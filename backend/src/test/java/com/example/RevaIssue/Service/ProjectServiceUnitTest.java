package com.example.RevaIssue.Service;

import com.example.RevaIssue.entity.Project;
import com.example.RevaIssue.entity.User;
import com.example.RevaIssue.repository.ProjectRepository;
import com.example.RevaIssue.repository.User_ProjectsRepository;
import com.example.RevaIssue.service.ProjectService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProjectServiceUnitTest {

    @Mock private ProjectRepository projectRepository;
    @Mock private User_ProjectsRepository userProjectsRepository;

    @InjectMocks
    private ProjectService projectService;

    @Test
    void createProjectTest(){
        // create the data
        Project mockProject = new Project();
        mockProject.setProjectName("Mock Project");
        mockProject.setProjectDescription("Mock Description");

        when(projectRepository.save(any(Project.class))).thenAnswer(i -> i.getArguments()[0]);

        // call logic
        Project result = projectService.createProject(mockProject);

        // assertions
        assertNotNull(result);
        assertEquals("Mock Project", result.getProjectName());
        verify(projectRepository, times(1)).save(mockProject);
    }

    @Test
    void getProjectByIdTest(){
        // create the data
        Project mockProject = new Project();
        mockProject.setProjectID(1);
        mockProject.setProjectName("Searchable Project");

        // mock behavior
        when(projectRepository.findById(1)).thenReturn(Optional.of(mockProject));

        // call logic
        Project result = projectService.getProjectById(1);

        // assertions
        assertNotNull(result);
        assertEquals(1, result.getProjectID());
        assertEquals("Searchable Project", result.getProjectName());
    }

    @Test
    void deleteProject_PositiveTest() {
        // create the data
        Project mockProject = new Project();
        mockProject.setProjectID(1);

        // mock behavior - findById must return something for the 'if' check
        when(projectRepository.findById(1)).thenReturn(Optional.of(mockProject));

        // call logic
        boolean result = projectService.deleteProject(1);

        // assertions
        assertTrue(result);
        verify(projectRepository, times(1)).deleteById(1);
    }

    @Test
    void deleteProject_NegativeTest() {
        // mock behavior - return empty to trigger the false branch
        when(projectRepository.findById(1)).thenReturn(Optional.empty());

        // call logic
        boolean result = projectService.deleteProject(1);

        // assertions
        assertFalse(result);
        verify(projectRepository, never()).deleteById(anyInt());
    }

    @Test
    void updateProjectTest() {
        // create the data
        Project existingProject = new Project();
        existingProject.setProjectID(1);
        existingProject.setProjectName("Old Name");
        existingProject.setProjectDescription("Old Description");

        // mock behavior
        // 1. Return existing project for the initial check
        when(projectRepository.findById(1)).thenReturn(Optional.of(existingProject));
        // 2. Return the same project for the final return statement
        when(projectRepository.save(any(Project.class))).thenReturn(existingProject);

        // call logic
        Project updatedProject = projectService.updateProject(1, "Updated Name", "Updated Description");

        // assertions
        assertNotNull(updatedProject);
        assertEquals("Updated Name", updatedProject.getProjectName());
        assertEquals("Updated Description", updatedProject.getProjectDescription());
        verify(projectRepository).save(existingProject);
    }

    @Test
    void getProjectsByKeywordTest() {
        // create the data
        Project mockProject = new Project();
        mockProject.setProjectName("New Project");
        List<Project> mockList = List.of(mockProject);

        // mock behavior
        when(projectRepository.findByKeyword("New")).thenReturn(mockList);

        // call logic
        List<Project> result = projectService.getProjectsByKeyword("New");

        // assertions
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("New Project", result.get(0).getProjectName());
    }

    @Test
    void getAllUsersByProjectTest() {
        // create the data
        Project mockProject = new Project();
        mockProject.setProjectID(10);

        User mockUser = new User();
        mockUser.setUsername("developer");

        List<User> mockUsers = List.of(mockUser);

        // mock behavior
        when(userProjectsRepository.findUsersByProjectId(10)).thenReturn(mockUsers);

        // call logic
        List<User> result = projectService.getAllUsersByProject(mockProject);

        // assertions
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("developer", result.getFirst().getUsername());
    }
}
