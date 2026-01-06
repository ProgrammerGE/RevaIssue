import { Injectable, WritableSignal } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { ProjectData } from '../interfaces/project-data';
import { HttpClient } from '@angular/common/http';

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

  constructor(private httpClient: HttpClient) {}

  getProjectSubject() {
    return this.projectSubject;
  }

  viewAllProjects(
    projects: WritableSignal<Array<String>>,
    role: 'admin' | 'developer' | 'tester'
  ): void {
    this.httpClient
      .get<ProjectData[]>(`${this.baseUrl}/${role}/projects`)
      .subscribe((projectList) => {
        const newProjectList = [];
        for (const projectObj of projectList) {
          newProjectList.push(projectObj.projectName);
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
    this.httpClient.post<ProjectData>(`${this.baseUrl}/admin/projects/new`, project).subscribe({
      next: (create) => {
        if (!create) this.projectSubject.next(create);
      },
      error: (err) => console.error('Error creating new project', err),
    });
  }
}
