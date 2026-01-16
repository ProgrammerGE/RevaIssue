package com.example.RevaIssue.Service;

import com.example.RevaIssue.dto.LoginRequest;
import com.example.RevaIssue.dto.RegisterRequest;
import com.example.RevaIssue.entity.User;
import com.example.RevaIssue.enums.UserRole;
import com.example.RevaIssue.service.AuthService;
import com.example.RevaIssue.service.UserService;
import com.example.RevaIssue.util.JwtUtility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceUnitTest {

    @Mock
    private JwtUtility jwtUtility;

    @Mock
    private UserService userService;

    @InjectMocks
    private AuthService authService;

    private User mockUser;
    private final String MOCK_TOKEN = "mock-token";
    private final String BEARER_TOKEN = "Bearer mock-token";

    @BeforeEach
    void setUp() {
        mockUser = new User();
        mockUser.setUser_ID(UUID.randomUUID());
        mockUser.setUsername("admin");
        mockUser.setPassword("password");
        mockUser.setUserRole(UserRole.ADMIN);
    }

    @Test
    void register_PositiveTest() {
        RegisterRequest request = new RegisterRequest("admin", "password", UserRole.ADMIN);

        // Mocking UserService.createUser to return the input (style: thenAnswer)
        when(userService.createUser(any(User.class))).thenAnswer(i -> i.getArguments()[0]);
        when(jwtUtility.generateAccessToken(anyString(), any(UserRole.class))).thenReturn(MOCK_TOKEN);

        String result = authService.register(request);

        assertNotNull(result);
        assertEquals(MOCK_TOKEN, result);
        verify(userService, times(1)).createUser(any(User.class));
    }

    @Test
    void login_PositiveTest() {
        LoginRequest request = new LoginRequest("admin", "password");

        when(userService.getUserByUsername("admin")).thenReturn(mockUser);
        when(jwtUtility.generateAccessToken("admin", UserRole.ADMIN)).thenReturn(MOCK_TOKEN);

        String result = authService.login(request);

        assertEquals(MOCK_TOKEN, result);
        verify(userService, times(1)).getUserByUsername("admin");
    }

    @Test
    void login_InvalidPassword_NegativeTest() {
        LoginRequest request = new LoginRequest("admin", "wrong-password");

        when(userService.getUserByUsername("admin")).thenReturn(mockUser);

        assertThrows(ResponseStatusException.class, () -> authService.login(request));
        verify(jwtUtility, never()).generateAccessToken(anyString(), any());
    }

    @Test
    void checkUserToken_PositiveTest() {
        when(jwtUtility.extractRole(MOCK_TOKEN)).thenReturn("ADMIN");

        boolean isValid = authService.checkUserToken(BEARER_TOKEN);

        assertTrue(isValid);
        verify(jwtUtility, times(1)).extractRole(MOCK_TOKEN);
    }

    @Test
    void checkUserToken_InvalidRole_NegativeTest() {
        when(jwtUtility.extractRole(MOCK_TOKEN)).thenReturn("GUEST");

        boolean isValid = authService.checkUserToken(BEARER_TOKEN);

        assertFalse(isValid);
    }

    @Test
    void checkUserToken_NullHeader_NegativeTest() {
        boolean isValid = authService.checkUserToken(null);

        assertFalse(isValid);
        verify(jwtUtility, never()).extractRole(anyString());
    }

    @Test
    void getRoleFromHeader_PositiveTest() {
        when(jwtUtility.extractRole(MOCK_TOKEN)).thenReturn("ADMIN");

        String role = authService.getRoleFromHeader(BEARER_TOKEN);

        assertEquals("ADMIN", role);
    }

    @Test
    void getUsernameFromHeader_PositiveTest() {
        when(jwtUtility.extractUsername(MOCK_TOKEN)).thenReturn("admin");

        String username = authService.getUsernameFromHeader(BEARER_TOKEN);

        assertEquals("admin", username);
    }

    @Test
    void checkUserToken_MalformedHeader_NegativeTest() {
        // test with a string that has no space
        boolean isValid = authService.checkUserToken("NotABearerToken");

        assertFalse(isValid);
        verify(jwtUtility, never()).extractRole(anyString());
    }

    @Test
    void checkUserToken_JwtException_NegativeTest() {
        // mock the utility to throw an exception (expired/invalid signature)
        when(jwtUtility.extractRole(MOCK_TOKEN)).thenThrow(new io.jsonwebtoken.JwtException("Invalid"));

        boolean isValid = authService.checkUserToken(BEARER_TOKEN);

        assertFalse(isValid);
    }

    @Test
    void checkUserToken_CaseInsensitiveRole_PositiveTest() {
        // test that "tester" (lowercase) is accepted if the token returns "TESTER"
        when(jwtUtility.extractRole(MOCK_TOKEN)).thenReturn("TESTER");

        boolean isValid = authService.checkUserToken(BEARER_TOKEN);

        assertTrue(isValid);
    }

    @Test
    void login_UserNotFound_NegativeTest() {
        LoginRequest request = new LoginRequest("unknown", "password");

        // simulate UserService throwing a 404
        when(userService.getUserByUsername("unknown"))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        assertThrows(ResponseStatusException.class, () -> authService.login(request));
    }
}