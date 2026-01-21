import { HttpClient } from '@angular/common/http';
import { Injectable, WritableSignal } from '@angular/core';
import { AuditLogData } from '../interfaces/audit-log-data';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class AuditLogService {

  private baseUrl = environment.apiUrl;

  constructor(private httpClient:HttpClient){}

  getAllAuditLogs(auditLogs: WritableSignal<Array<AuditLogData>>): void{
    this.httpClient.get<AuditLogData[]>('${this.baseUrl}/admin/audits')
    .subscribe( auditLogsList => {
        const newAuditLogs = [];
        for(const auditObj of auditLogsList){
          newAuditLogs.push(auditObj);
        }
        auditLogs.set(newAuditLogs);
    });
  }
}
