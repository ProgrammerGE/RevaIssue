package com.example.RevaIssue.util;
import com.example.RevaIssue.enums.UserRole;

public record UserDTO(
        String username,
        UserRole role
) {}
