import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BorrowerComponent } from './borrower.component';
import { FormsModule } from '@angular/forms';
import {RouterTestingModule} from '@angular/router/testing';
import {RouterModule, Routes, Router} from '@angular/router';
describe('BorrowerComponent', () => {
  let component: BorrowerComponent;
  let fixture: ComponentFixture<BorrowerComponent>;
  let router: Router;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [FormsModule, RouterModule.forRoot([])],
      declarations: [ BorrowerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BorrowerComponent);
    router = TestBed.get(Router);
    fixture.detectChanges();
  });

  it('should create', () => {
    const component = fixture.componentInstance;
    expect(component).toBeTruthy();
  });

  it('should navigate with searchBook', ()=>{
    const component = fixture.componentInstance;
    const navigateSpy = spyOn(router, 'navigate');
    component.searchBookForm = "";
    component.searchBook();
    expect(navigateSpy).toHaveBeenCalledWith(['/lms/borrower/search-book'], {queryParams: {search: component.searchBookForm}});
  });

  it('should navigate with searchLoan', ()=>{
    const component = fixture.componentInstance;
    const navigateSpy = spyOn(router, 'navigate');
    component.searchLoanForm = new Int16Array(1);
    component.searchLoan();
    expect(navigateSpy).toHaveBeenCalledWith(['/lms/borrower/loan'], {queryParams: {cardNo: component.searchLoanForm}});
  });

});
