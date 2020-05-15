import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CheckLoansComponent } from './check-loans.component';

describe('CheckLoansComponent', () => {
  let component: CheckLoansComponent;
  let fixture: ComponentFixture<CheckLoansComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CheckLoansComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CheckLoansComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
