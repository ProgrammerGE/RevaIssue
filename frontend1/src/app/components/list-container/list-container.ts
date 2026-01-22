import { Component, input, InputSignal, signal, computed, Input, Signal, model, ModelSignal, output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { hubListItem } from '../../interfaces/hubpage-list-item';
import { CapitalizeFirst } from '../../pipes/capitalize-first.pipe';
import { CreateProject } from "../create-project/create-project";
import { HubMenuContext } from '../../interfaces/hub-menu-context';

@Component({
  selector: 'app-list-container',
  imports: [CommonModule, CapitalizeFirst, CreateProject],
  templateUrl: './list-container.html',
  styleUrl: './list-container.css',
})
export class ListContainer {
  [x: string]: any;
  title = input<string>('Title');
  isExpanded = signal(true);
  //TODO: Refactor, id, can be null until updated
  items: InputSignal<hubListItem[]> = input([
    { id: 999, name: 'placeholder title', description: 'placeholder description' },
  ]);
  itemCount = computed(() => this.items().length);
  hasButton: InputSignal<boolean> = input(true);
  // showContext = model(false);
  contextMenuRequested = output<HubMenuContext>();
  @Input() listItems: hubListItem[] = [];
  @Input() itemClicked?: (item: hubListItem) => void;
  @Input() userRole: string = '';
  isAdmin: Signal<boolean> = computed(() => this.userRole.toLowerCase() === 'admin'); // Following example in hub-page.ts

  constructor() {}

  expandList() {
    this.isExpanded.update((v) => !v);
  }

  onContext(event: MouseEvent, item: hubListItem) {
    event.preventDefault();
    this.contextMenuRequested.emit({ xPos: event.clientX, yPos: event.clientY, item: item })
  }
}