export interface AuditLog{    
    audit_id: number,
    timestamp: string,
    action: string,
    username: string,
    role: string
}

export interface AuditLogData {
    auditLogs: Array<AuditLog>
}
