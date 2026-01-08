import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PopupWrapper } from './popup-wrapper';

describe('PopupWrapper', () => {
  let component: PopupWrapper;
  let fixture: ComponentFixture<PopupWrapper>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PopupWrapper]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PopupWrapper);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
