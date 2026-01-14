package com.example.RevaIssue.Service;

import com.example.RevaIssue.entity.Project;
import com.example.RevaIssue.service.ProjectService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class ProjectServiceUnitTest {

    @Mock
    private ProjectService projectService;

    @Test
    void createProjectTest(){
        Project mockProject = new Project();
        mockProject.setProjectName("Mock Project");
        mockProject.setProjectDescription("Mock Description");
        mockProject.setProjectID(1);

        when(projectService.getProjectById(1)).thenReturn(mockProject);

        Project targetProject = projectService.createProject(mockProject);

        assertNotNull(targetProject);
        assertEquals(mockProject, targetProject);
    }

    @Test
    void getProjectByIdTest(){
        Project mockProject = new Project();
        mockProject.setProjectName("Mock Project");
        mockProject.setProjectDescription("Mock Description");
        mockProject.setProjectID(1);

        when(projectService.createProject(mockProject)).thenReturn(mockProject);

        Project project = projectService.getProjectById(1);
        assertNotNull(project);
        assertEquals(mockProject, project);
    }

    @Test
    void deleteProjectTest(){
        Project mockProject = new Project();
        mockProject.setProjectName("Mock Project");
        mockProject.setProjectDescription("Mock Description");
        mockProject.setProjectID(1);

        when(projectService.createProject(mockProject)).thenReturn(mockProject);

        boolean projectDelete = projectService.deleteProject(1);
        assertTrue(projectDelete);
    }

    @Test
    void updateProjectTest(){
        Project mockProject = new Project();
        mockProject.setProjectName("New Name");
        mockProject.setProjectDescription("New Description");
        mockProject.setProjectID(1);

        Project mockUpdatedProject = new Project();
        mockUpdatedProject.setProjectName("Updated Name");
        mockUpdatedProject.setProjectDescription("Updated Description");
        mockUpdatedProject.setProjectID(1);

        when(projectService.createProject(mockProject)).thenReturn(mockProject);

        Project updatedProject = projectService.updateProject(1, "Updated Name", "Updated Description");
        assertNotNull(updatedProject);
        assertEquals(mockUpdatedProject, updatedProject);
    }

    @Test
    void getProjectByKeywordTest(){
        Project mockProject = new Project();
        mockProject.setProjectName("New Name");
        mockProject.setProjectDescription("New Description");
        mockProject.setProjectID(1);

        List<Project> mockProjectList = new ArrayList<>();
        mockProjectList.add(mockProject);

        when(projectService.createProject(mockProject)).thenReturn(mockProject);

        List<Project> projectList = projectService.getProjectsByKeyword("New");
        assertNotNull(projectList);
        assertEquals(projectList.getFirst(), mockProjectList.getFirst());
    }
}
