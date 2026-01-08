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
  isPoppedUp: WritableSignal<boolean> = signal(false);

  titleMissing: boolean = false;
  descriptionMissing: boolean = false;

  @Input()
  projectID: number = 0;

  constructor(
    private popUpService: PopUpService,
    private projectService: ProjectService,
    private router: Router
  ) {
    this.popUpService.getPopUpUpdateSubject().subscribe((popUpSetting) => {
      this.isPoppedUp.set(popUpSetting);
    });
  }

  addUpdatePopup() {
    this.popUpService.openUpdatePopup();
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
      this.isPoppedUp.set(false);
      this.projectTitle = '';
      this.projectDesc = '';
    }

    if (this.projectTitle == '') {
      this.titleMissing = true;
    }

    if (this.projectDesc == '') {
      this.descriptionMissing = true;
    }
  }

  cancelUpdate() {
    this.isPoppedUp.set(false);
  }
}
