import { HttpClient } from '@angular/common/http';
import { Injectable, WritableSignal } from '@angular/core';
import { AuditLogData } from '../interfaces/audit-log-data';

@Injectable({
  providedIn: 'root',
})
export class AuditLogService {

  constructor(private httpClient:HttpClient){}

  getAllAuditLogs(auditLogs: WritableSignal<Array<AuditLogData>>): void{
    this.httpClient.get<AuditLogData[]>('http://localhost:8080/admin/audits')
    .subscribe( auditLogsList => {
        const newAuditLogs = [];
        for(const auditObj of auditLogsList){
          newAuditLogs.push(auditObj);
        }
        auditLogs.set(newAuditLogs);
    });
  }
}
