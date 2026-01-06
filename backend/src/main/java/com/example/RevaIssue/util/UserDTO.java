package com.example.RevaIssue.util;
import com.example.RevaIssue.entity.User;
import com.example.RevaIssue.enums.UserRole;

import java.util.UUID;

public record UserDTO(
        String username,
        UserRole userRole
) {}
