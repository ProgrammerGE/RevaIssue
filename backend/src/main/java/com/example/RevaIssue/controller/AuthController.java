package com.example.RevaIssue.controller;

import com.example.RevaIssue.dto.LoginRequest;
import com.example.RevaIssue.dto.RegisterRequest;
import com.example.RevaIssue.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser (@RequestBody LoginRequest request){
        String token = authService.login(request);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser (@RequestBody RegisterRequest request){
        String token = authService.register(request);
        return ResponseEntity.ok(token);
    }
}
