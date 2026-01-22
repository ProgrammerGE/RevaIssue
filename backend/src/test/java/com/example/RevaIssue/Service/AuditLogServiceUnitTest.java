package com.example.RevaIssue.Service;

import com.example.RevaIssue.entity.AuditLog;
import com.example.RevaIssue.repository.AuditLogRepository;
import com.example.RevaIssue.service.AuditLogService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuditLogServiceUnitTest {

    @InjectMocks
    private AuditLogService auditLogService;
    @Mock
    private AuditLogRepository auditLogRepository;

    @Test
    void createAuditLogTest(){
        AuditLog mockAuditLog = new AuditLog("UPDATED", "username", "admin");
        when(auditLogRepository.save(any(AuditLog.class)))
                .thenAnswer(i -> i.getArguments()[0]);

        AuditLog auditLog = auditLogService.createAuditLog(mockAuditLog);
        assertNotNull(auditLog);
        assertEquals("UPDATED", auditLog.getAction());
        verify(auditLogRepository, times(1)).save(mockAuditLog);
        assertEquals(mockAuditLog, auditLog);
    }

    @Test
    void getAllAuditLogs(){
        // mock the data
        AuditLog log1 = new AuditLog("UPDATED", "user1", "admin");
        AuditLog log2 = new AuditLog("CREATED", "user2", "guest");
        AuditLog mockAuditLog = new AuditLog("UPDATED", "username", "admin");
        List<AuditLog> mockList = Arrays.asList(log1, log2, mockAuditLog);

        when(auditLogRepository.findAll()).thenReturn(mockList);
        // try to access all audit logs
        List<AuditLog> result = auditLogService.getAllAuditLogs();

        // assertions
        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals(log1, result.getFirst());
        verify(auditLogRepository, times(1)).findAll(); // make sure repository is called only once
    }
}
