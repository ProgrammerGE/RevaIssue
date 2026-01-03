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
        User user = userService.getUserByUsername(request.username());
        if(!user.getPassword().equals(request.password())){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid Password");
        }
        return jwtUtility.generateAccessToken(user.getUsername(), user.getUserRole());
    }

    public String register(RegisterRequest request){
        User newUser = new User();
        newUser.setUsername(request.username());
        newUser.setPassword(request.password());
        newUser.setUserRole(request.role());
        User savedUser = userService.createUser(newUser);
        return jwtUtility.generateAccessToken(savedUser.getUsername(), savedUser.getUserRole());
    }

    public boolean checkUserToken(String headerData){
        if (headerData == null){
            return false;
        }
        try{
            String token = headerData.split(" ") [1];
            String role = this.jwtUtility.extractRole(token);
            return  role.equalsIgnoreCase("admin")
                    || role.equalsIgnoreCase("tester")
                    || role.equalsIgnoreCase("developer") ;
        }catch (JwtException e){
            return false;
        }
    }
}