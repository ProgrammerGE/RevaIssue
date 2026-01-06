package com.example.RevaIssue.util;

import com.example.RevaIssue.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserDTO toDTO(User user) {
        return new UserDTO(
                user.getUsername(),
                user.getUserRole()
        );
    }
}
