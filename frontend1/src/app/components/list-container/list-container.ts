import { Component, input, InputSignal, signal, computed } from '@angular/core';
import { CommonModule } from '@angular/common';
import { hubListItem } from '../../interfaces/hubpage-list-item';
import { PopUpService } from '../../services/pop-up-service';
import { CapitalizeFirst } from '../../pipes/capitalize-first.pipe';

@Component({
  selector: 'app-list-container',
  imports: [CommonModule, CapitalizeFirst],
  templateUrl: './list-container.html',
  styleUrl: './list-container.css',
})
export class ListContainer {
  [x: string]: any;
  title = input<string>('Title');
  isExpanded = signal(true);
  items: InputSignal<hubListItem[]> = input([
    { name: 'placeholder title', description: 'placeholder description' },
  ]);
  itemCount = computed(() => this.items().length);
  hasButton : InputSignal<boolean> = input(true);

  constructor(private popUpService: PopUpService) {}

  expandList() {
    this.isExpanded.update((v) => !v);
  }

  addPopup(){
      this.popUpService.openPopUpProject();
  }
}
