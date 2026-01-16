package com.example.RevaIssue.service;

import com.example.RevaIssue.dto.LoginRequest;
import com.example.RevaIssue.dto.RegisterRequest;
import com.example.RevaIssue.entity.User;
import com.example.RevaIssue.util.JwtUtility;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {

    private final JwtUtility jwtUtility;
    private final UserService userService;

    public AuthService (UserService userService, JwtUtility jwtUtility){
        this.userService = userService;
        this.jwtUtility = jwtUtility;
    }

    public String login(LoginRequest request) {
        User user = userService.getUserByUsername(request.username().toLowerCase());
        if(!user.getPassword().equals(request.password())){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid Password");
        }
        return jwtUtility.generateAccessToken(user.getUsername(), user.getUserRole());
    }

    public String register(RegisterRequest request){
        User newUser = new User();
        newUser.setUsername(request.username().toLowerCase());
        newUser.setPassword(request.password());
        newUser.setUserRole(request.role());
        User savedUser = userService.createUser(newUser);
        return jwtUtility.generateAccessToken(savedUser.getUsername(), savedUser.getUserRole());
    }

    public boolean checkUserToken(String headerData) {
        // check header data to ensure the format of "Prefix Token". Apparently it can potentially cause a 500 error.
        if (headerData == null || !headerData.contains(" ")) {
            return false;
        }
        try {
            String token = headerData.split(" ")[1];
            String role = this.jwtUtility.extractRole(token);
            return role.equalsIgnoreCase("admin")
                    || role.equalsIgnoreCase("tester")
                    || role.equalsIgnoreCase("developer");
        } catch (Exception e) { // catching Exception instead of JwtException to also handle potential String.split errors
            return false;
        }
    }

    public String getRoleFromHeader(String authHeader){
        String token = authHeader.split(" ")[1];
        return this.jwtUtility.extractRole(token);
    }

    public String getUsernameFromHeader(String authHeader){
        String token = authHeader.split(" ")[1];
        return this.jwtUtility.extractUsername(token);
    }
}