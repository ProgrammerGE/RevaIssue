
package com.example.RevaIssue.Repository;

import com.example.RevaIssue.entity.User;
import com.example.RevaIssue.enums.UserRole;
import com.example.RevaIssue.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestPropertySource(locations = "classpath:test.properties")
public class UserRepositoryIntegrationTest {
    private UserRepository userRepository;

    @Autowired
    public UserRepositoryIntegrationTest(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    /**
     * Taken from Eric's example
     */
    @Test
    void findByUsernamePositiveTest() {
        User user = new User();
//        avoid setting the UUID in your test, will cause an error
//        user.setUserId(UUID.randomUUID());
        user.setUsername("testuser");
        user.setPassword("testpass");
        user.setUserRole(UserRole.ADMIN);
        userRepository.save(user);
        Optional<User> found = userRepository.findByUsername("testuser");
        assertTrue(found.isPresent());
        assertEquals("testuser", found.get().getUsername());
        assertEquals("testpass", found.get().getPassword());
    }

    @Test
    void findByUsernameNegativeTest() {
        Optional<User> found = userRepository.findByUsername("wronguser");
        assertFalse(found.isPresent());
    }
}
