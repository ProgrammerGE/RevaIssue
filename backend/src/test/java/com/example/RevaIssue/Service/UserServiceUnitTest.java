package com.example.RevaIssue.Service;

import com.example.RevaIssue.entity.Project;
import com.example.RevaIssue.entity.User;
import com.example.RevaIssue.entity.User_Projects;
import com.example.RevaIssue.enums.UserRole;
import com.example.RevaIssue.repository.ProjectRepository;
import com.example.RevaIssue.repository.UserRepository;
import com.example.RevaIssue.repository.User_ProjectsRepository;
import com.example.RevaIssue.service.ProjectService;
import com.example.RevaIssue.service.UserService;
import com.example.RevaIssue.util.UserDTO;
import com.example.RevaIssue.util.UserMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceUnitTest {

    // test candidate
    @InjectMocks
    private UserService userService;
    // mock dependencies
    @Mock
    private ProjectService projectService; // todo: delete if not needed (we may need this).
    @Mock
    private UserRepository userRepository;
    @Mock
    private ProjectRepository projectRepository;
    @Mock
    private User_ProjectsRepository userProjectsRepository;
    @Mock
    private UserMapper userMapper;


    @Test
    void createUserTest(){
        // create the data
        User inputUser = new User();
        inputUser.setUsername("admin");

        // mock the repository behavior
        when(userRepository.save(any(User.class))).thenReturn(inputUser);

        // call the logic in the service
        User result = userService.createUser(inputUser);

        // assertions
        assertNotNull(result);
        assertEquals("admin", result.getUsername());
    }

    @Test
    void getUserByIdTest(){
        // create the data
        UUID id = UUID.randomUUID();
        User mockUser = new User();
        mockUser.setUser_ID(id);
        mockUser.setUsername("admin");
        mockUser.setPassword("password");
        mockUser.setUserRole(UserRole.ADMIN);

        // mock the repository behavior
        // We mock the findById call to return our user
        when(userRepository.findById(id)).thenReturn(Optional.of(mockUser));

        // call the logic in the service
        User targetUser = userService.getUserById(id);

        // assertions
        assertNotNull(targetUser);
        assertEquals(id, targetUser.getUser_ID());
        assertEquals("admin", targetUser.getUsername());
    }

    @Test
    void getUserByUsernameTest(){
        // create the data
        User mockUser = new User();
        mockUser.setUsername("admin");
        mockUser.setPassword("password");
        mockUser.setUserRole(UserRole.ADMIN);

        // mock the repository behavior
        when(userRepository.findByUsername("admin")).thenReturn(Optional.of(mockUser));

        // call the logic in the service
        User targetUser = userService.getUserByUsername("admin");

        // assertions
        assertNotNull(targetUser);
        assertEquals("admin", targetUser.getUsername());
    }

    @Test
    void getAllUsersByProjectIdTest(){
        // create the data
        UserDTO mockDto = new UserDTO("admin", UserRole.ADMIN);
        User mockUser = new User();
        mockUser.setUsername("admin");
        mockUser.setPassword("password");
        mockUser.setUserRole(UserRole.ADMIN);

        List<User> mockList = List.of(mockUser);

        // mock the repository behavior
        when(userProjectsRepository.findUsersByProjectId(1)).thenReturn(mockList);

        // mock the mapper behavior
        when(userMapper.toDTO(mockUser)).thenReturn(mockDto);

        // call the logic in the service
        List<UserDTO> result = userService.getAllUsersByProjectId(1);

        // assertions
        assertNotNull(result);
        assertEquals(1, result.size());
        // Note: changed "tester" to "admin" to match your mockUser setup
        assertEquals("admin", result.getFirst().username());
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
