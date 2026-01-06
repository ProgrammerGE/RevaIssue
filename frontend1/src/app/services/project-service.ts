import { Injectable, WritableSignal } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { ProjectData } from '../interfaces/project-data';
import { HttpClient } from '@angular/common/http';
import { JwtTokenStorage } from './jwt-token-storage';

@Injectable({
  providedIn: 'root',
})
export class ProjectService {
  private projectSubject = new BehaviorSubject<ProjectData>({
    projectID: 0,
    projectName: '',
    projectDescription: '',
  });

  private baseUrl = 'http://localhost:8080';

  constructor(private httpClient: HttpClient, private tokenStorage: JwtTokenStorage) {}

  getProjectSubject() {
    return this.projectSubject;
  }

  // TODO: user role should only be of type admin | developer | tester
  viewAllProjects(
    projects: WritableSignal<Array<ProjectData>>,
    role: string
  ): void {
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

  // viewAllProjects(projects: WritableSignal<ProjectData[]>): void {
  //   this.httpClient
  //     .get<ProjectData[]>(`${this.baseUrl}/common/projects`)
  //     .subscribe((projectList) => {
  //       projects.set(projectList);
  //     });
  // }

  viewProject(projectId: number): void {
    this.httpClient.get<ProjectData>(`${this.baseUrl}/common/projects/${projectId}`).subscribe({
      next: (project) => this.projectSubject.next(project),
      error: (err) => console.error('Error loading project', err),
    });
  }

  updateProject(projectId: number, project: Partial<ProjectData>): void {
    this.httpClient
      .put<ProjectData>(`${this.baseUrl}/admin/projects${projectId}`, project)
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
        next: (create) => {
          if (!create) this.projectSubject.next(create);
        },
        error: (err) => console.error('Error creating new project', err),
      });
  }
}
