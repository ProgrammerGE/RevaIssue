import { Component, Input, input, model, signal, WritableSignal } from '@angular/core';

@Component({
  selector: 'app-search-bar',
  imports: [],
  templateUrl: './search-bar.html',
  styleUrl: './search-bar.css',
})
export class SearchBar {
  active = model.required<boolean>();

  onClick(event: Event){
    this.active.set(!this.active());
    event.preventDefault();
  }
}
