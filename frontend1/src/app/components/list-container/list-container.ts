import { Component, input, InputSignal, signal, computed } from '@angular/core';
import { CommonModule } from '@angular/common';
import { hubListItem } from '../../interfaces/hubpage-list-item';
import { PopUpService } from '../../services/pop-up-service';

@Component({
  selector: 'app-list-container',
  imports: [CommonModule],
  templateUrl: './list-container.html',
  styleUrl: './list-container.css',
})
export class ListContainer {
  title = input<string>('Title');
  isExpanded = signal(true);
  items: InputSignal<hubListItem[]> = input([{ name: 'placeholder title', description: 'placeholder description' }]);
  itemCount = computed(() => this.items().length);

  constructor(private popUpService: PopUpService){}

  expandList() {
    this.isExpanded.update((v) => !v);
  }

  addProjectPopup(){
    this.popUpService.openPopUp();
  }
}

