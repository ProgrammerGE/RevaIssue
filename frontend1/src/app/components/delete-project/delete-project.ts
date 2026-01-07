import { Component, Input, signal, WritableSignal } from '@angular/core';
import { PopUpService } from '../../services/pop-up-service';
import { ProjectService } from '../../services/project-service';

@Component({
  selector: 'app-delete-project',
  imports: [],
  templateUrl: './delete-project.html',
  styleUrl: './delete-project.css',
})
export class DeleteProject {  
  @Input()
  projectID: number = 1;
  projectName: WritableSignal<string> = signal('');
  buttonText = 'Delete Project';
  buttonCancel = 'Cancel';
  isPoppedUp: WritableSignal<boolean> = signal(false);

  constructor(private popUpService: PopUpService, private projectService: ProjectService){
    this.popUpService.getPopUpDeleteSubject().subscribe((popUpSetting) => {
      this.isPoppedUp.set(popUpSetting);
    });
    this.projectService.getProjectSubject().subscribe(projectData => {
      this.projectName.set(projectData.projectName);
    })
  }

  deleteProject(){
    this.projectService.deleteProjectByID(this.projectID);
    this.isPoppedUp.set(false);
  }  

  cancelCreation() {
    this.isPoppedUp.set(false);
  }

}
