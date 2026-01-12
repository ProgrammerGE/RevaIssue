import { Component, model } from '@angular/core';

@Component({
  selector: 'app-popup-wrapper',
  imports: [],
  templateUrl: './popup-wrapper.html',
  styleUrl: './popup-wrapper.css',
})

export class PopupWrapper {
  isPopupActive = model(false);
  
  onKeyDown = (event: KeyboardEvent) => {
    if(event.key === "Escape") {
      this.isPopupActive.set(false);
    }
  }

  closePopup() {
    this.isPopupActive.set(false);
  }

  ngOnInit() {
    window.addEventListener('keydown', this.onKeyDown);
  }

  ngOnDestroy() {
    window.removeEventListener('keydown', this.onKeyDown);
  }
}
