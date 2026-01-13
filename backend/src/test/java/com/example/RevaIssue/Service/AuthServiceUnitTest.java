package com.example.RevaIssue.Service;

import com.example.RevaIssue.dto.LoginRequest;
import com.example.RevaIssue.dto.RegisterRequest;
import com.example.RevaIssue.entity.User;
import com.example.RevaIssue.enums.UserRole;
import com.example.RevaIssue.service.AuthService;
import com.example.RevaIssue.service.UserService;
import com.example.RevaIssue.util.JwtUtility;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

/**
 * This a test file for the AuthService java file
 */
public class AuthServiceUnitTest {

    @Mock
    private JwtUtility jwtUtility;
    @Mock
    private UserService userService;
    @Mock
    private AuthService authService;

    @Test
    void userRegistrationTest(){
        User mockUser = new User();
        mockUser.setUser_ID(UUID.randomUUID());
        mockUser.setUsername("admin");
        mockUser.setPassword("password");
        mockUser.setUserRole(UserRole.ADMIN);

        String token = authService.register( new RegisterRequest(
                mockUser.getUsername(), mockUser.getPassword(), mockUser.getUserRole()));

        assertNotNull(token);
    }

    /**
     * The login and registration test will follow a similar format
     */
    @Test
    void userLoginTest(){
        User mockUser = new User();
        mockUser.setUser_ID(UUID.randomUUID());
        mockUser.setUsername("admin");
        mockUser.setPassword("password");
        mockUser.setUserRole(UserRole.ADMIN);

        when(userService.getUserById(mockUser.getUser_ID())).thenReturn(mockUser);
        when(jwtUtility.generateAccessToken(mockUser.getUsername(), mockUser.getUserRole()))
                .thenReturn("mock-token");

        String token = authService.login(new LoginRequest(mockUser.getUsername(), mockUser.getPassword()));
        assertNotNull(token);
        assertEquals("mock-token", token);
    }

    @Test
    void checkUserTokenTest(){
        User mockUser = new User();
        mockUser.setUser_ID(UUID.randomUUID());
        mockUser.setUsername("admin");
        mockUser.setPassword("password");
        mockUser.setUserRole(UserRole.ADMIN);

        when(userService.getUserById(mockUser.getUser_ID())).thenReturn(mockUser);
        when(jwtUtility.generateAccessToken(mockUser.getUsername(), mockUser.getUserRole()))
                .thenReturn("token admin");

        assertTrue(authService.checkUserToken("token admin"));
    }

    @Test
    void getRoleFromHeaderTest(){
        User mockUser = new User();
        mockUser.setUser_ID(UUID.randomUUID());
        mockUser.setUsername("admin");
        mockUser.setPassword("password");
        mockUser.setUserRole(UserRole.ADMIN);

        when(userService.getUserById(mockUser.getUser_ID())).thenReturn(mockUser);
        when(jwtUtility.generateAccessToken(mockUser.getUsername(), mockUser.getUserRole()))
                .thenReturn("token admin");

        String role = authService.getRoleFromHeader("token admin");
        assertNotNull(role);
        assertEquals("admin", role);
    }

    @Test
    void getUsernameFromHeaderTest(){
        User mockUser = new User();
        mockUser.setUser_ID(UUID.randomUUID());
        mockUser.setUsername("user");
        mockUser.setPassword("password");
        mockUser.setUserRole(UserRole.ADMIN);

        when(userService.getUserById(mockUser.getUser_ID())).thenReturn(mockUser);
        when(jwtUtility.generateAccessToken(mockUser.getUsername(), mockUser.getUserRole()))
                .thenReturn("token user");

        String username = authService.getRoleFromHeader("token user");
        assertNotNull(username);
        assertEquals("user", username);
    }
}
