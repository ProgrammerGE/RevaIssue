package com.example.RevaIssue.dto;

import com.example.RevaIssue.enums.UserRole;

public record RegisterRequest(String username, String password, UserRole role) {
}
