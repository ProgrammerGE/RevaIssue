package com.example.RevaIssue.Service;

import com.example.RevaIssue.entity.AuditLog;
import com.example.RevaIssue.enums.UserRole;
import com.example.RevaIssue.service.AuditLogService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

public class AuditLogServiceUnitTest {

    @Mock
    private AuditLogService auditLogService;

    @Test
    void createAuditLogTest(){
        AuditLog mockAuditLog = new AuditLog("UPDATED", "username", "admin");

        AuditLog auditLog = auditLogService.createAuditLog(mockAuditLog);
        assertNotNull(auditLog);
        assertEquals(mockAuditLog, auditLog);
    }

    @Test
    void getAllAuditLogs(){
        AuditLog mockAuditLog = new AuditLog("UPDATED", "username", "admin");

        List<AuditLog> mockAuditLogList = new ArrayList<>();
        mockAuditLogList.add(mockAuditLog);

        when(auditLogService.createAuditLog(mockAuditLog)).thenReturn(mockAuditLog);

        List<AuditLog> auditLogList = auditLogService.getAllAuditLogs();
        assertNotNull(auditLogList);
        assertEquals(auditLogList.getFirst(), mockAuditLogList.getFirst());
    }
}
