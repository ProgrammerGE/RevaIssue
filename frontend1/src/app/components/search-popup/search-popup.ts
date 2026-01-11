import {
  afterNextRender,
  Component,
  ElementRef,
  input,
  model,
  ModelSignal,
  viewChild,
} from '@angular/core';
import { PopupWrapper } from '../popup-wrapper/popup-wrapper';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-search-popup',
  imports: [PopupWrapper, RouterLink],
  templateUrl: './search-popup.html',
  styleUrl: './search-popup.css',
})
export class SearchPopup {
  placeholder = input('Search');
  isPopupActive = model(false);
  inputValue: ModelSignal<string> = model('');
  searchInput = viewChild<ElementRef<HTMLInputElement>>('searchInput');

  constructor() {
    afterNextRender(() => {
      this.searchInput()?.nativeElement.focus();
    });
  }

  onType(e: Event) {
    const input = e.target as HTMLInputElement;
    this.inputValue.set(input.value);
  }

  closePopup() {
    this.isPopupActive.set(false);
  }
}
