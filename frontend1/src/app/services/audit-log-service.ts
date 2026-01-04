import { HttpClient } from '@angular/common/http';
import { Injectable, WritableSignal } from '@angular/core';
import { AuditLog, AuditLogData } from '../interfaces/audit-log-data';

@Injectable({
  providedIn: 'root',
})
export class AuditLogService {

  constructor(private httpClient:HttpClient){}

  getAllAuditLogs(auditLogs: WritableSignal<Array<AuditLog>>){
    this.httpClient.get<AuditLogData>('http://localhost:8080/admin/audits')
    .subscribe( auditLogsList => {
        const newAuditLogs = [];
        for(const auditObj of auditLogsList.auditLogs){
          newAuditLogs.push(auditObj);
        }
        auditLogs.set(newAuditLogs);
    });
  }
}
