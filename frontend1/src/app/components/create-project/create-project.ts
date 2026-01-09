import { Component, Input, isWritableSignal, signal, WritableSignal } from '@angular/core';
import { ProjectService } from '../../services/project-service';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { Subject } from 'rxjs';
import { PopUpService } from '../../services/pop-up-service';

@Component({
  selector: 'app-create-project',
  imports: [FormsModule],
  templateUrl: './create-project.html',
  styleUrl: './create-project.css',
})
export class CreateProject {
  projectTitle: string = '';
  projectDesc: string = '';
  buttonText = 'Create Project';
  buttonCancel = 'Cancel';
  @Input() isPoppedUp: boolean = false;

  titleMissing: boolean = false;
  descriptionMissing: boolean = false;

  constructor(
    private popUpService: PopUpService,
    private projectService: ProjectService,
    private router: Router
  ) {}

  
  addPopup() {
      this.isPoppedUp = true;
  }

  createProject() {
    //Following the same format as on the project.ts file
    this.titleMissing = false;
    this.descriptionMissing = false;

    if (this.projectTitle != '' && this.projectDesc != '') {
      this.projectService.createProject({
        projectName: this.projectTitle,
        projectDescription: this.projectDesc,
      });
      this.isPoppedUp = false;
      this.projectTitle = '';
      this.projectDesc = '';
      window.location.reload();
    }

    if (this.projectTitle == '') {
      this.titleMissing = true;
    }

    if (this.projectDesc == '') {
      this.descriptionMissing = true;
    }
  }

  cancelCreation() {
    this.isPoppedUp = false;
    this.projectTitle = '';
    this.projectDesc = '';
    window.location.reload();
  }
}
