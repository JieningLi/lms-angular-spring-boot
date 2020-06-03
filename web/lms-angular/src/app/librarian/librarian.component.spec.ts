import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LibrarianComponent } from './librarian.component';
import { FormsModule } from '@angular/forms';
import { RouterTestingModule } from '@angular/router/testing';
import { Router } from '@angular/router';
describe('LibrarianComponent', () => {
  let component: LibrarianComponent;
  let fixture: ComponentFixture<LibrarianComponent>;
  let router: Router;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [FormsModule, RouterTestingModule.withRoutes([])],
      declarations: [ LibrarianComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LibrarianComponent);
    router = TestBed.get(Router);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should navigate with searchLibrary', ()=>{
    const navigateSpy = spyOn(router, 'navigate');
    component.searchLibraryForm = new Int16Array(1);
    component.searchLibrary();
    expect(navigateSpy).toHaveBeenCalledWith(['/lms/librarian/library'], {queryParams: {branchNo: component.searchLibraryForm}});
  });

  it('should navigate with searchLoans', ()=>{
    const component = fixture.componentInstance;
    const navigateSpy = spyOn(router, 'navigate');
    component.searchCardNoForm = new Int16Array(1);
    component.searchLoan();
    expect(navigateSpy).toHaveBeenCalledWith(['/lms/librarian/loan'], {queryParams: {cardNo: component.searchCardNoForm}});
  });
});
