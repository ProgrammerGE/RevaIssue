package com.example.RevaIssue.controller;

import com.example.RevaIssue.dto.JwtTransport;
import com.example.RevaIssue.dto.LoginRequest;
import com.example.RevaIssue.dto.RegisterRequest;
import com.example.RevaIssue.service.AuthService;
import io.jsonwebtoken.Jwt;
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
}
