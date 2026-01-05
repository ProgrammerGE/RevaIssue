package com.example.RevaIssue.controller;

import com.example.RevaIssue.dto.JwtTransport;
import com.example.RevaIssue.dto.LoginRequest;
import com.example.RevaIssue.dto.RegisterRequest;
import com.example.RevaIssue.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtTransport> loginUser (@RequestBody LoginRequest request){
        String token = authService.login(request);
        JwtTransport response = new JwtTransport(token);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<JwtTransport> registerUser (@RequestBody RegisterRequest request){
        String token = authService.register(request);
        JwtTransport response = new JwtTransport(token);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user") //We are checking if the user is logged in as either an admin, tester, or developer
    public  ResponseEntity<Void> validateUserToken(@RequestHeader String authorization){
        boolean isAuthorized = authService.checkUserToken(authorization);
        if(isAuthorized){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        } else {
            return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }
}
