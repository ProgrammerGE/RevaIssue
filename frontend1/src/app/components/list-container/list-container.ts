import { Component, input, InputSignal, signal, computed, Input, Signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { hubListItem } from '../../interfaces/hubpage-list-item';
import { PopUpService } from '../../services/pop-up-service';
import { CapitalizeFirst } from '../../pipes/capitalize-first.pipe';
import { Router } from '@angular/router';
import { DeleteProject } from '../delete-project/delete-project';
import { UpdateProject } from '../update-project/update-project';

@Component({
  selector: 'app-list-container',
  imports: [CommonModule, CapitalizeFirst, DeleteProject, UpdateProject],
  templateUrl: './list-container.html',
  styleUrl: './list-container.css',
})
export class ListContainer {
  [x: string]: any;
  title = input<string>('Title');
  isExpanded = signal(true);
  items: InputSignal<hubListItem[]> = input([
    { id: 999, name: 'placeholder title', description: 'placeholder description' },
  ]);
  itemCount = computed(() => this.items().length);
  hasButton: InputSignal<boolean> = input(true);

  @Input() listItems: hubListItem[] = [];
  @Input() itemClicked?: (item: hubListItem) => void;
  @Input() userRole: string = "";
  isAdmin: Signal<boolean> = computed(() => this.userRole.toLowerCase() === 'admin'); // Following example in hub-page.ts

  constructor(private popUpService: PopUpService, private router: Router) {}

  expandList() {
    this.isExpanded.update((v) => !v);
  }

  addPopup() {
    this.popUpService.openPopUpProject();
  }
}
