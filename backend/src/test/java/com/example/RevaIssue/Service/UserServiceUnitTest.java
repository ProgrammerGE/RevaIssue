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
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceUnitTest {

    @Mock private UserRepository userRepository;
    @Mock private User_ProjectsRepository userProjectsRepository;
    @Mock private ProjectRepository projectRepository;
    @Mock private UserMapper userMapper;

    @InjectMocks private UserService userService;

    @Test
    void createUserTest(){
        // create the data - testing toLowerCase logic
        User inputUser = new User();
        inputUser.setUsername("Admin");

        when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArguments()[0]);

        // call the logic
        User result = userService.createUser(inputUser);

        // assertions
        assertEquals("admin", result.getUsername()); // Verifies the .toLowerCase() logic
    }

    @Test
    void getUserByUsername_NotFoundTest() {
        // mock the repository to return empty
        when(userRepository.findByUsername("missing")).thenReturn(Optional.empty());

        // assertions
        assertThrows(ResponseStatusException.class, () -> userService.getUserByUsername("missing"));
    }

    @Test
    void getProjectsByIdTest() {
        // create the data
        UUID userId = UUID.randomUUID();
        User mockUser = new User();
        mockUser.setUser_ID(userId);

        Project proj1 = new Project();
        proj1.setProjectID(101);
        Project proj2 = new Project();
        proj2.setProjectID(102);

        User_Projects up1 = new User_Projects();
        up1.setProject(proj1);
        User_Projects up2 = new User_Projects();
        up2.setProject(proj2);

        List<User_Projects> mockJoinTableRecords = List.of(up1, up2);

        // mock the repository behavior
        // 1. Mock the user lookup
        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));
        // 2. Mock the join table lookup
        when(userProjectsRepository.findByUser(mockUser)).thenReturn(mockJoinTableRecords);

        // call the logic in the service
        List<Project> result = userService.getProjectsById(userId);

        // assertions
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(101, result.get(0).getProjectID());
        assertEquals(102, result.get(1).getProjectID());
    }

    @Test
    void deleteUser_NonAdminTest() {    // because admins can't be deleted using the service, as a safety guard
        // create the data
        User user = new User();
        user.setUserRole(UserRole.TESTER);

        // call the logic
        userService.deleteUser(user);

        // assertions
        // verify that delete was actually called once
        verify(userRepository, times(1)).delete(user);
    }

    @Test
    void getAllUsersByProjectIdTest(){
        // create the data
        User mockUser = new User();
        List<User> mockList = List.of(mockUser);
        UserDTO mockDto = new UserDTO("admin", UserRole.ADMIN);

        // mock behavior
        when(userProjectsRepository.findUsersByProjectId(1)).thenReturn(mockList);
        when(userMapper.toDTO(mockUser)).thenReturn(mockDto);

        // call logic
        List<UserDTO> result = userService.getAllUsersByProjectId(1);

        // assertions
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("admin", result.get(0).username());
    }

    @Test
    void assignProjectPositiveTest(){
        // create the data
        User mockUser = new User();
        Project mockProject = new Project();
        User_Projects savedLink = new User_Projects();
        savedLink.setUser(mockUser);
        savedLink.setProject(mockProject);

        // mock behavior
        when(userRepository.findByUsername("tester")).thenReturn(Optional.of(mockUser));
        when(projectRepository.findById(1)).thenReturn(Optional.of(mockProject));
        when(userProjectsRepository.save(any(User_Projects.class))).thenReturn(savedLink);

        // call logic
        User_Projects result = userService.assignProject(1, "tester");

        // assertions
        assertNotNull(result);
        verify(userProjectsRepository).save(any(User_Projects.class));
    }

    @Test
    void revokeProjectPositiveTest(){
        // mock behavior - repo returns 1 for one row deleted
        when(userProjectsRepository.deleteByUsernameAndProjectId("tester", 1)).thenReturn(1);

        // call logic
        boolean result = userService.revokeProject(1, "tester");

        // assertions
        assertTrue(result);
    }

    @Test
    void revokeProjectNegativeTest(){
        // mock a database crash
        when(userProjectsRepository.deleteByUsernameAndProjectId(anyString(), anyInt()))
                .thenThrow(new RuntimeException("DB Error"));

        // call logic
        boolean result = userService.revokeProject(1, "tester");

        // assertions
        assertFalse(result);
    }
}