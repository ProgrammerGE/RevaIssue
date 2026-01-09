import { Component, Input, signal, WritableSignal } from '@angular/core';
import { PopUpService } from '../../services/pop-up-service';
import { ProjectService } from '../../services/project-service';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-update-project',
  imports: [FormsModule],
  templateUrl: './update-project.html',
  styleUrl: './update-project.css',
})
export class UpdateProject {
  @Input() projectTitle: string = '';
  @Input() projectDesc: string = '';
  buttonText = 'Update Project';
  buttonCancel = 'Cancel';
  @Input() isPoppedUp: boolean = false;

  titleMissing: boolean = false;
  descriptionMissing: boolean = false;

  @Input()
  projectID: number = 0;

  constructor(
    private popUpService: PopUpService,
    private projectService: ProjectService,
    private router: Router
  ) { }

  addUpdatePopup() {
    this.isPoppedUp = true;
  }

  updateProject(){
    //Following the same format as on the project.ts file
    this.titleMissing = false;
    this.descriptionMissing = false;

    if (this.projectTitle != '' && this.projectDesc != '') {
      this.projectService.updateProject( this.projectID, {
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

  cancelUpdate() {
    this.isPoppedUp = false;
    window.location.reload();
  }
}
