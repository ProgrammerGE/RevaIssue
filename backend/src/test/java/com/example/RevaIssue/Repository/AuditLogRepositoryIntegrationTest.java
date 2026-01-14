package com.example.RevaIssue.Repository;

import com.example.RevaIssue.entity.AuditLog;
import com.example.RevaIssue.repository.AuditLogRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestPropertySource(locations = "classpath:test.properties")
public class AuditLogRepositoryIntegrationTest {
    private final AuditLogRepository auditLogRepository;

    @Autowired
    public AuditLogRepositoryIntegrationTest(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    // simple smoke test
    @Test
    void findById(){
        // add a log to repo
        AuditLog log = new AuditLog();
        log.setRole("USER");
        log.setUsername("admin");
        log.setAction("test action");
        log.setTimeLogged(LocalDateTime.now());

        log = auditLogRepository.save(log);

        // find by id
        AuditLog result = auditLogRepository.findById(log.getLogID()).orElse(null);

        // assertions
        assertNotNull(result);
        assertEquals(result, log);
    }
}
