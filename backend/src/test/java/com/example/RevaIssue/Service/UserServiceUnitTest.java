package com.example.RevaIssue.Service;

import com.example.RevaIssue.entity.Project;
import com.example.RevaIssue.entity.User;
import com.example.RevaIssue.entity.User_Projects;
import com.example.RevaIssue.enums.UserRole;
import com.example.RevaIssue.service.AuditLogService;
import com.example.RevaIssue.service.ProjectService;
import com.example.RevaIssue.service.UserService;
import com.example.RevaIssue.util.UserDTO;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class UserServiceUnitTest {

    @Mock
    private UserService userService;
    @Mock
    private ProjectService projectService;

    @Test
    void createUserTest(){
        User mockUser = new User();
        mockUser.setUser_ID(UUID.randomUUID());
        mockUser.setUsername("admin");
        mockUser.setPassword("password");
        mockUser.setUserRole(UserRole.ADMIN);

        when(userService.getUserById(mockUser.getUser_ID())).thenReturn(mockUser);

        User targetUser = userService.createUser(mockUser);

        assertNotNull(targetUser);
        assertEquals(mockUser, targetUser);
    }

    @Test
    void getUserByIdTest(){
        User mockUser = new User();
        UUID id = UUID.randomUUID();
        mockUser.setUser_ID(id);
        mockUser.setUsername("admin");
        mockUser.setPassword("password");
        mockUser.setUserRole(UserRole.ADMIN);

        when(userService.createUser(mockUser)).thenReturn(mockUser);

        User targetUser = userService.getUserById(id);
        assertNotNull(targetUser);
        assertEquals(mockUser, targetUser);
    }

    @Test
    void getUserByUsernameTest(){
        User mockUser = new User();
        UUID id = UUID.randomUUID();
        mockUser.setUser_ID(id);
        mockUser.setUsername("admin");
        mockUser.setPassword("password");
        mockUser.setUserRole(UserRole.ADMIN);

        when(userService.createUser(mockUser)).thenReturn(mockUser);

        User targetUser = userService.getUserByUsername(mockUser.getUsername());
        assertNotNull(targetUser);
        assertEquals(mockUser, targetUser);
    }

    @Test
    void getAllUsersByProjectIdTest(){
        User mockUser = new User();
        mockUser.setUser_ID(UUID.randomUUID());
        mockUser.setUsername("tester");
        mockUser.setPassword("password");
        mockUser.setUserRole(UserRole.TESTER);

        Project mockProject = new Project();
        mockProject.setProjectID(1);
        mockProject.setProjectName("Mock Project");
        mockProject.setProjectDescription("Mock Description");

        User_Projects user_project = new User_Projects();
        user_project.setUser(mockUser);
        user_project.setProject(mockProject);
        user_project.setID(1);

        List<UserDTO> mockUserDTOList = new ArrayList<>();
        mockUserDTOList.add(new UserDTO("tester", UserRole.TESTER));

        when(userService.getAllUsersByProjectId(1)).thenReturn(mockUserDTOList);

        List<UserDTO> userDTOList = userService.getAllUsersByProjectId(1);
        assertNotNull(userDTOList);
        assertEquals(userDTOList.getFirst().username(), mockUserDTOList.getFirst().username());
        assertEquals(userDTOList.getFirst().role(), mockUserDTOList.getFirst().role());
    }

    @Test
    void assignProjectPositiveTest(){
        User mockUser = new User();
        mockUser.setUser_ID(UUID.randomUUID());
        mockUser.setUsername("tester");
        mockUser.setPassword("password");
        mockUser.setUserRole(UserRole.TESTER);

        Project mockProject = new Project();
        mockProject.setProjectID(1);
        mockProject.setProjectName("Mock Project");
        mockProject.setProjectDescription("Mock Description");

        User_Projects mockUser_project = new User_Projects();
        mockUser_project.setUser(mockUser);
        mockUser_project.setProject(mockProject);
        mockUser_project.setID(1);

        when(userService.createUser(mockUser)).thenReturn(mockUser);
        when(projectService.createProject(mockProject)).thenReturn(mockProject);

        User_Projects targetUserProj = userService.assignProject(1, "tester");
        assertNotNull(targetUserProj);
        assertEquals(targetUserProj.getUser(), mockUser);
        assertEquals(targetUserProj.getProject(), mockProject);
    }

    @Test
    void assignProjectNegativeTest(){

        when(userService.getProjectsById(UUID.randomUUID())).thenReturn(null);

        assertThrows(RuntimeException.class, () -> userService.assignProject(1, "failure"));
    }

    @Test
    void revokeProjectPositiveTest(){
        User mockUser = new User();
        mockUser.setUser_ID(UUID.randomUUID());
        mockUser.setUsername("tester");
        mockUser.setPassword("password");
        mockUser.setUserRole(UserRole.TESTER);

        Project mockProject = new Project();
        mockProject.setProjectID(1);
        mockProject.setProjectName("Mock Project");
        mockProject.setProjectDescription("Mock Description");

        User_Projects user_project = new User_Projects();
        user_project.setUser(mockUser);
        user_project.setProject(mockProject);
        user_project.setID(1);

        when(userService.createUser(mockUser)).thenReturn(mockUser);
        when(projectService.createProject(mockProject)).thenReturn(mockProject);

        boolean userRevoked = userService.revokeProject(1, "tester");
        assertTrue(userRevoked);
    }

    @Test
    void revokeProjectNegativeTest(){
        when(userService.revokeProject(1, "tester")).thenReturn(false);

        boolean userRevoked = userService.revokeProject(1, "tester");
        assertTrue(userRevoked);
    }
}
