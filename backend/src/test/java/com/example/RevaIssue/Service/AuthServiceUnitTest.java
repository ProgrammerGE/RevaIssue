package com.example.RevaIssue.Service;

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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

        when(userService.getUserById(mockUser.getUser_ID())).thenReturn(mockUser);
        when(jwtUtility.generateAccessToken(mockUser.getUsername(), mockUser.getUserRole()))
                .thenReturn("mock-token");

        String token = authService.register( new RegisterRequest(
                mockUser.getUsername(), mockUser.getPassword(), mockUser.getUserRole()));
        assertNotNull(token);
        assertEquals("mock-token", token);
    }
}
