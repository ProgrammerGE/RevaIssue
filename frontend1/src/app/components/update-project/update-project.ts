import { Component, Input, model, output } from '@angular/core';
import { ProjectService } from '../../services/project-service';
import { FormsModule } from '@angular/forms';
import { PopupWrapper } from '../popup-wrapper/popup-wrapper';
import { ProjectUpdate } from '../../interfaces/project-update';

@Component({
  selector: 'app-update-project',
  imports: [FormsModule, PopupWrapper],
  templateUrl: './update-project.html',
  styleUrl: './update-project.css',
})
export class UpdateProject {
  @Input() projectTitle: string = '';
  @Input() projectDesc: string = '';
  titleMissing: boolean = false;
  descriptionMissing: boolean = false;
  isPopupActive = model(false);
  updateEvent = output<ProjectUpdate>();

  constructor(private projectService: ProjectService) {}

  clickCancel() {
    this.isPopupActive.set(false);
  }

  clickUpdate() {
    if (this.projectTitle == '') {
      this.titleMissing = true;
    }
    if (this.projectDesc == '') {
      this.descriptionMissing = true;
      return;
    }
    this.updateEvent.emit({ title: this.projectTitle, description: this.projectDesc });
  }
}