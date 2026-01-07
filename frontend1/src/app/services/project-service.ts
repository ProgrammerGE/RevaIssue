import { Injectable, WritableSignal } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { ProjectData } from '../interfaces/project-data';
import { HttpClient } from '@angular/common/http';
import { JwtTokenStorage } from './jwt-token-storage';
import { UserAssignment } from '../interfaces/user-assignment';

@Injectable({
  providedIn: 'root',
})
export class ProjectService {
  private projectSubject = new BehaviorSubject<ProjectData>({
    projectID: 0,
    projectName: '',
    projectDescription: '',
  });
  private projectsListSubject = new BehaviorSubject<ProjectData[]>([]);

  private baseUrl = 'http://localhost:8080';

  constructor(private httpClient: HttpClient, private tokenStorage: JwtTokenStorage) {}

  getProjectSubject() {
    return this.projectSubject;
  }

  addUserToProject(projectId: number, userName: string): void {
    this.httpClient
      .post<UserAssignment>(`${this.baseUrl}/admin/projects/${projectId}/assign/${userName}`, {
        username: userName,
        projectID: projectId,
      })
      .subscribe();
  }

  removeUserFromProject(projectId: number, userName: string): void {
    this.httpClient
      .delete(`${this.baseUrl}/admin/projects/${projectId}/revoke/${userName}`)
      .subscribe();
  }

  // TODO: user role should only be of type admin | developer | tester
  viewAllProjects(projects: WritableSignal<Array<ProjectData>>, role: string): void {
    this.httpClient
      .get<ProjectData[]>(`${this.baseUrl}/${role}/projects`)
      .subscribe((projectList) => {
        const newProjectList = [];
        for (const projectObj of projectList) {
          newProjectList.push(projectObj);
        }
        projects.set(newProjectList);
      });
  }

  viewProject(projectId: number): void {
    this.httpClient.get<ProjectData>(`${this.baseUrl}/common/projects/${projectId}`).subscribe({
      next: (project) => this.projectSubject.next(project),
      error: (err) => console.error('Error loading project', err),
    });
  }

  updateProject(projectId: number, project: Partial<ProjectData>): void {
    this.httpClient
      .put<ProjectData>(`${this.baseUrl}/admin/projects/${projectId}`, project)
      .subscribe({
        next: (updatedProject) => this.projectSubject.next(updatedProject),
        error: (err) => console.error('Error updating project', err),
      });
  }

  createProject(project: Partial<ProjectData>): void {
    const headers = {
      Authorization: `Bearer ${this.tokenStorage.getToken()}`,
    };
    this.httpClient
      .post<ProjectData>(`${this.baseUrl}/admin/projects/new`, project, { headers })
      .subscribe({
        next: (createdProject) => {
          const current = this.projectsListSubject.value;
          this.projectsListSubject.next([...current, createdProject]);
        },
        error: (err) => console.error('Error creating new project', err),
      });
  }

  viewAllProjectsByKeyword(keyword: String, projects: WritableSignal<Array<ProjectData>>) {
    this.httpClient
      .get<ProjectData[]>(`${this.baseUrl}/common/projects/${keyword}`)
      .subscribe((projectList) => {
        const newProjectList = [];
        for (const projObj of projectList) {
          newProjectList.push(projObj);
        }
        projects.set(newProjectList);
      });
  }
}
