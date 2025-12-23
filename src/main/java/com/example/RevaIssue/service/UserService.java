package com.example.RevaIssue.service;

import com.example.RevaIssue.entity.User;
import com.example.RevaIssue.repository.UserRepository;
import com.example.RevaIssue.repository.User_ProjectsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional //causes every public method to be transactional

/*
The user service provides logic for editing the users SQL table, viewing information about users through the
User_Projects SQL table.
 */
public class UserService {
    private final UserRepository userRepository;
    private final User_ProjectsRepository userProjectsRepository;

    public UserService(UserRepository userRepository, User_ProjectsRepository userProjectsRepository) {
        this.userRepository = userRepository;
        this.userProjectsRepository = userProjectsRepository;
    }

    public User createUser(User user) {
        // TODO : Make sure the user's id and username are both unique before returning
        // TODO : Make sure the user has a role before allowing creation
        return userRepository.save(user);
    }

    public void deleteUser(User user) {
        // TODO : Ensure admins cannot be deleted
        // TODO : Ensure only admins can delete other users
        userRepository.delete(user);
    }

    public User getUserById(UUID id) {
        // consider throwing an error instead of returning null
        return userRepository.findById(id).orElse(null);
    }
}
