import { Component, Input, model, output } from '@angular/core';
import { PopupWrapper } from '../popup-wrapper/popup-wrapper';

@Component({
  selector: 'app-delete-project',
  imports: [PopupWrapper],
  templateUrl: './delete-project.html',
  styleUrl: './delete-project.css',
})
export class DeleteProject { 
  @Input()
  projectID: number = 0;
  @Input() projectName: string = '';
  @Input() isPoppedUp: boolean = false;
  isPopupActive = model(false);
  deleteEvent = output();

  constructor(){
  }

  clickConfirm(){
    this.isPopupActive.set(false);
    this.deleteEvent.emit();
    window.location.reload();
  }

  clickCancel() {
    this.isPopupActive.set(false);
  }
}
