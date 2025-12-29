package com.example.RevaIssue.service;

import com.example.RevaIssue.entity.AuditLog;
import com.example.RevaIssue.repository.AuditLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AuditLogService {

    @Autowired
    private AuditLogRepository auditLogRepository;

    public AuditLog createAuditLog(AuditLog auditLog){
        return auditLogRepository.save(auditLog);
    }

    public List<AuditLog> getAllAuditLogs(){
        return auditLogRepository.findAll();
    }
}
