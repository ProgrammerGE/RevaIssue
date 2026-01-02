import { Injectable, WritableSignal } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { ProjectData } from '../interfaces/project-data';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class ProjectService {
  private projectSubject = new BehaviorSubject<ProjectData>({
    project_id: 0,
    project_name: '',
    project_description: '',
  });

  private baseUrl = 'http://localhost:8080';

  constructor(private httpClient: HttpClient) {}

  getProjectSubject() {
    return this.projectSubject;
  }

  viewAllProjects(projects: WritableSignal<Array<ProjectData>>, role: 'admin' | 'developer' | 'tester') : void{
    this.httpClient.get<ProjectData[]>(`${this.baseUrl}/${role}/projects`)
    .subscribe( projectList => {
      const newProjectList = [];
      for(const projectObj of projectList){
        newProjectList.push(projectObj);
      }
      projects.set(newProjectList);
    });
  }

  viewProject(projectId: number, role: 'admin' | 'developer' | 'tester'): void {
    this.httpClient.get<ProjectData>(`${this.baseUrl}/${role}/projects/${projectId}`).subscribe({
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
}
