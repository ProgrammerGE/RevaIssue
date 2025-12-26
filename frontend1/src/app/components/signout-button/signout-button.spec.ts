import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SignoutButton } from './signout-button';

describe('SignoutButton', () => {
  let component: SignoutButton;
  let fixture: ComponentFixture<SignoutButton>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SignoutButton]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SignoutButton);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
