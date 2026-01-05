import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListContainer } from './list-container';

describe('ListContainer', () => {
  let component: ListContainer;
  let fixture: ComponentFixture<ListContainer>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ListContainer]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ListContainer);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
