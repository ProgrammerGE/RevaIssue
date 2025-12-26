import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { ProjectData } from '../interfaces/project-data';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class ProjectService {
  private projectSubject: BehaviorSubject<ProjectData>;

  // wont be httpClient it will talk to backend
  constructor(private httpClient: HttpClient) {
    this.projectSubject = new BehaviorSubject<ProjectData>({
      project_id: 0,
      project_name: '',
      project_description: '',
    });
  }

  getProjectSubject() {
    return this.projectSubject;
  }
}
