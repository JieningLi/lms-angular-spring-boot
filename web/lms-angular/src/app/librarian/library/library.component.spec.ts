import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LibraryComponent } from './library.component';
import { NgbModal, NgbModalRef, NgbModule } from "@ng-bootstrap/ng-bootstrap";
import { PagerService } from "src/app/common/pager.service";
import { LmsService } from "src/app/common/lms.service";
import { ActivatedRoute, Router, RouterModule } from "@angular/router";
import {RouterTestingModule} from '@angular/router/testing';
import { FormsModule } from '@angular/forms';
import { HttpClientModule, HttpErrorResponse } from '@angular/common/http';
import { of, throwError } from 'rxjs';

//Mock Modal Ref Class
export class MockNgbModalRef{
  result: Promise<any> = new Promise((resolve, reject) => resolve("mock"));
}

describe('LibraryComponent', () => {
  let component: LibraryComponent;
  let fixture: ComponentFixture<LibraryComponent>;
  let lmsService: LmsService;
  let modalService: NgbModal;
  let mockModalRef: MockNgbModalRef = new MockNgbModalRef();
  let pagerService: PagerService;
  let router: Router;
  let route: ActivatedRoute;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [ FormsModule, RouterTestingModule.withRoutes([]), HttpClientModule],
      declarations: [ LibraryComponent ],
      providers:[ LmsService, PagerService]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LibraryComponent);
    router = TestBed.get(Router);
    lmsService = new LmsService(null);
    pagerService = new PagerService();
    modalService = TestBed.get(NgbModal);
    route = TestBed.get(ActivatedRoute);
    component = new LibraryComponent(lmsService, modalService, pagerService, router, route);

    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should load all components and run life cycle', ()=>{
    spyOn(component, "loadLibrary");
    spyOn(component, "loadBookCopies");
    component.ngOnInit();
    //tests
    expect(component.loadLibrary).toHaveBeenCalled;
    expect(component.loadBookCopies).toHaveBeenCalled;
  });

  it('should load library via mock-service - return mockdata', ()=>{
    const mockLibrary = {
      id: 1,
      name: "Test Library 1",
      address: "Test Address 1"
    };
    expect(lmsService).toBeTruthy();
    spyOn(lmsService, "getAll").and.returnValue(of(mockLibrary));
    component.loadLibrary();
    expect(component.connection).toBe(true);
    expect(component.library).toBe(mockLibrary);
    expect(component.nameInput).toBe("Test Library 1");
    expect(component.addressInput).toBe("Test Address 1");
    expect(component.editName).toBe(false);
    expect(component.editAddress).toBe(false);
  });

  it('should load library via mock-service - fail return error message', ()=>{
 
    expect(lmsService).toBeTruthy();
    spyOn(lmsService, "getAll").and.returnValue(throwError({status: 404}));
    component.loadLibrary();
    expect(component.connection).toBe(false);
    expect(component.errMsg).toBe("Cannot connect to server. Error at loading branch. ");
  });
});
