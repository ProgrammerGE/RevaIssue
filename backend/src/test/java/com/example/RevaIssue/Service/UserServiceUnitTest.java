package com.example.RevaIssue.Service;

import com.example.RevaIssue.entity.Project;
import com.example.RevaIssue.entity.User;
import com.example.RevaIssue.entity.User_Projects;
import com.example.RevaIssue.enums.UserRole;
import com.example.RevaIssue.repository.ProjectRepository;
import com.example.RevaIssue.repository.UserRepository;
import com.example.RevaIssue.repository.User_ProjectsRepository;
import com.example.RevaIssue.service.UserService;
import com.example.RevaIssue.util.UserDTO;
import com.example.RevaIssue.util.UserMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceUnitTest {

    // test candidate
    @InjectMocks
    private UserService userService;
    // mock dependencies
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
        // create the data
        User mockUser = new User();
        mockUser.setUsername("tester");

        Project mockProject = new Project();
        mockProject.setProjectID(1);

        User_Projects mockUserProject = new User_Projects();
        mockUserProject.setUser(mockUser);
        mockUserProject.setProject(mockProject);

        // mock the repository behavior
        when(userRepository.findByUsername("tester")).thenReturn(Optional.of(mockUser));
        when(projectRepository.findById(1)).thenReturn(Optional.of(mockProject));
        when(userProjectsRepository.save(any(User_Projects.class))).thenReturn(mockUserProject);

        // call the logic in the service
        User_Projects result = userService.assignProject(1, "tester");

        // assertions
        assertNotNull(result);
        assertEquals("tester", result.getUser().getUsername());
        assertEquals(1, result.getProject().getProjectID());
    }

    @Test
    void assignProjectNegativeTest(){
        // mock the repository behavior
        // simulate user not found to trigger a RuntimeException in the service
        when(userRepository.findByUsername("failure")).thenReturn(Optional.empty());

        // assertions
        assertThrows(RuntimeException.class, () -> userService.assignProject(1, "failure"));
    }

    @Test
    void revokeProjectPositiveTest(){
        // mock the repository behavior
        // simulate that 1 row was successfully deleted
        when(userProjectsRepository.deleteByUsernameAndProjectId("tester", 1)).thenReturn(1);

        // call the logic in the service
        boolean userRevoked = userService.revokeProject(1, "tester");

        // assertions
        assertTrue(userRevoked);

        // verify the repository was actually called with these exact parameters
        verify(userProjectsRepository).deleteByUsernameAndProjectId("tester", 1);
    }

    @Test
    void revokeProjectNegativeTest(){
        // mock the repository behavior
        // simulate an exception being thrown
        // this triggers the catch block in the service
        when(userProjectsRepository.deleteByUsernameAndProjectId("tester", 1))
                .thenThrow(new RuntimeException("Database error"));

        // call the logic in the service
        boolean userRevoked = userService.revokeProject(1, "tester");

        // assertions
        // the catch block prints the error and returns false
        assertFalse(userRevoked);
    }
}
