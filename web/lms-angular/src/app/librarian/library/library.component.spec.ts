import { fakeAsync, async, ComponentFixture, TestBed, tick } from '@angular/core/testing';

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

  it('should load unlisted books', () =>{
    const mockUnlistedBooks = [{
      id: 1,
      title: "Test Book 1",
      authors: [{
        id: 1,
        name: "Test Author 1"}],
      },
      {
        id: 2,
        title: "Test Book 2",
        authors: [{
          id: 1,
          name: "Test Author 1"}],
      },
      {
        id: 3,
        title: "Test Book 3",
        authors: [{
          id: 1,
          name: "Test Author 1"}],
      }
    ];
    expect(lmsService).toBeTruthy();
    spyOn(lmsService, "getAll").and.returnValue(of(mockUnlistedBooks));
    component.loadUnlistedBooks();
    expect(component.connection).toBe(true);
    expect(component.booksNotInLibrary.length).toBe(3);
  });

  it('should fail unlisted books', () =>{
    expect(lmsService).toBeTruthy();
    spyOn(lmsService, "getAll").and.returnValue(throwError({status:404}));
    component.loadUnlistedBooks();
    expect(component.connection).toBe(false);
    expect(component.errMsg).toBe("Error in loading unlisted books.");
  });

  it('should load book copies', ()=>{
    const mockBookCopies = [{
      bookCopiesId:{
        bookId: 1,
        branchId: 1
      },
      book:{
        id: 1,
        title: "Test Title 1",
        authors:[{
          id: 1,
          name: "Test Author 1"
        }]
      },
      branch:{
        id: 1,
        name: "Test Library 1",
        address: "Test Address 1"
      },
      noOfCopies: 1
      },
      {
        bookCopiesId:{
          bookId: 2,
          branchId: 1
        },
        book:{
          id: 2,
          title: "Test Title 2",
          authors:[{
            id: 1,
            name: "Test Author 1"
          }]
        },
        branch:{
          id: 1,
          name: "Test Library 1",
          address: "Test Address 1"
        },
        noOfCopies: 1
        },
        {
          bookCopiesId:{
            bookId: 3,
            branchId: 1
          },
          book:{
            id: 3,
            title: "Test Title 3",
            authors:[{
              id: 1,
              name: "Test Author 1"
            }]
          },
          branch:{
            id: 1,
            name: "Test Library 1",
            address: "Test Address 1"
          },
          noOfCopies: 1
          },
    ];
    expect(lmsService).toBeTruthy();
    spyOn(lmsService,"getAll").and.returnValue(of(mockBookCopies));
    component.loadBookCopies();
    expect(component.connection).toBe(true);
    expect(component.bookCopies).toBe(mockBookCopies);
    expect(component.books)
    expect(spyOn(component,"loadUnlistedBooks")).toHaveBeenCalled;
    });

    it('should fail to load book copies', ()=>{
      expect(lmsService).toBeTruthy();
      spyOn(lmsService,"getAll").and.returnValue(throwError({status:404}));
      component.loadBookCopies();
      expect(component.connection).toBe(false);
      expect(component.errMsg).toBe("Cannot connect to server. Error at loading book copies. ");
    });

    it('should update Library', ()=>{
      const mockLibrary = {
        id: 1,
        name: "Test Library 1",
        address: "Test Address 1"
      };
      const mockNameInput = "Test Library 2";
      const mockAddressInput = "Test Address 2";
      component.library = mockLibrary;
      component.nameInput = mockNameInput;
      component.addressInput = mockAddressInput;
      spyOn(lmsService, "putObj").and.returnValue(of(mockLibrary));
      spyOn(lmsService, "getAll").and.returnValue(of(mockLibrary));
      component.updateLibrary();
      expect(spyOn(component, "loadLibrary")).toHaveBeenCalled;
      expect(spyOn(modalService, "dismissAll")).toHaveBeenCalled;
    });

    it('should updateLibrary fail', ()=>{
      const mockLibrary = {
        id: 1,
        name: "Test Library 1",
        address: "Test Address 1"
      };
      const mockNameInput = "Test Library 2";
      const mockAddressInput = "Test Address 2";
      component.library = mockLibrary;
      component.nameInput = mockNameInput;
      component.addressInput = mockAddressInput;
      spyOn(lmsService, "putObj").and.returnValue(throwError({status: 404}));
      component.updateLibrary();
      expect(component.errMsg).toBe("Error at updating branch. ");
      expect(component.connection).toBe(false);
    });

    it('should addBookCopy work', ()=>{
      const mockSelectedBook = {
        id: 1,
        title: "Test Book 1",
        authors:[{
          id: 1,
          name: "Test Author 1" 
        }]
      };
      const mockLibrary = {
        id: 1,
        name: "Test Library 1",
        address: "Test Address 1"
      };
      component.library = mockLibrary;
      component.selectedBook = mockSelectedBook;
      component.noOfCopiesInput = 1;
      spyOn(lmsService, "postObj").and.returnValue(of(mockLibrary));
      spyOn(component, "loadBookCopies");
      component.addBookCopy()
      expect(component.loadBookCopies).toHaveBeenCalled;
      expect(component.addBookForm).toBe(false);
    });

    it('should addBookCopy fail', ()=>{
      const mockSelectedBook = {
        id: 1,
        title: "Test Book 1",
        authors:[{
          id: 1,
          name: "Test Author 1" 
        }]
      };
      const mockLibrary = {
        id: 1,
        name: "Test Library 1",
        address: "Test Address 1"
      };
      component.library = mockLibrary;
      component.selectedBook = mockSelectedBook;
      component.noOfCopiesInput = 1;
      spyOn(lmsService, "postObj").and.returnValue(throwError({status: 400}));
  
      component.addBookCopy()
      expect(component.errMsg).toBe("Error at adding Book Copy.");
      expect(component.connection).toBe(false);
      expect(component.addBookForm).toBe(false);
    });

    it('should updateBookCopy work', ()=> {
      const mockSelectedBookCopy = {
        bookCopiesId:{
          bookId: 1,
          branchId: 1
        },
        book:{
          id:1,
          title:"Test Title 1",
          authors:[{id:1, name: "Test"}]
        },
        branch:{
          id: 1,
          name: "Test Branch 1",
          address: "Test Address 1"
        },
        noOfCopies: 1
      };
      component.selectedCopies = mockSelectedBookCopy;
      component.noOfCopiesInput = 2;
      spyOn(lmsService, "putObj").and.returnValue(of(mockSelectedBookCopy));
      spyOn(lmsService, "getAll").and.returnValue(throwError({status: 400}));
      component.updateBookCopy();
      expect(spyOn(component, "loadBookCopies")).toHaveBeenCalled;

      expect(spyOn(component, "closeEditRow")).toHaveBeenCalled;
    });

    it('should updateBookCopy fail', ()=> {
      const mockSelectedBookCopy = {
        bookCopiesId:{
          bookId: 1,
          branchId: 1
        },
        book:{
          id:1,
          title:"Test Title 1",
          authors:[{id:1, name: "Test"}]
        },
        branch:{
          id: 1,
          name: "Test Branch 1",
          address: "Test Address 1"
        },
        noOfCopies: 1
      };
      component.selectedCopies = mockSelectedBookCopy;
      component.noOfCopiesInput = 1;
      spyOn(lmsService, "putObj").and.returnValue(throwError({error: {message: "error!"}}));
      component.updateBookCopy();
      expect(component.errMsg).toBe("error!");
      expect(component.connection).toBe(false);
      expect(spyOn(component, "closeEditRow")).toHaveBeenCalled;
    });

    it('edit row should be working', ()=>{
      const mockSelectedBookCopy = {
        bookCopiesId:{
          bookId: 1,
          branchId: 1
        },
        book:{
          id:1,
          title:"Test Title 1",
          authors:[{id:1, name: "Test"}]
        },
        branch:{
          id: 1,
          name: "Test Branch 1",
          address: "Test Address 1"
        },
        noOfCopies: 1
      };
      component.editRow(mockSelectedBookCopy, 1);
      expect(component.selectedCopies).toBe(mockSelectedBookCopy);
      expect(component.editRowIndex).toEqual(1);
      expect(component.editNoOfCopies).toBe(true);
      expect(component.noOfCopiesInput).toEqual(1);
    });

    it("should open modal", fakeAsync(()=>{
      const mockBookCopies = [{
        bookCopiesId:{
          bookId: 1,
          branchId: 1
        },
        book:{
          id: 1,
          title: "Test Title 1",
          authors:[{
            id: 1,
            name: "Test Author 1"
          }]
        },
        branch:{
          id: 1,
          name: "Test Library 1",
          address: "Test Address 1"
        },
        noOfCopies: 1
        },
        {
          bookCopiesId:{
            bookId: 2,
            branchId: 1
          },
          book:{
            id: 2,
            title: "Test Title 2",
            authors:[{
              id: 1,
              name: "Test Author 1"
            }]
          },
          branch:{
            id: 1,
            name: "Test Library 1",
            address: "Test Address 1"
          },
          noOfCopies: 1
          },
          {
            bookCopiesId:{
              bookId: 3,
              branchId: 1
            },
            book:{
              id: 3,
              title: "Test Title 3",
              authors:[{
                id: 1,
                name: "Test Author 1"
              }]
            },
            branch:{
              id: 1,
              name: "Test Library 1",
              address: "Test Address 1"
            },
            noOfCopies: 1
            },
      ];
      spyOn(modalService, "open").and.returnValue(mockModalRef);
      component.open("bookCopiesModal");
    }));

    it("should close modal", fakeAsync(()=>{
      const mockBook = {
        id: 1,
        title: "Test Book 1",
        authors:[{
          id: 1,
          name: "Test Author 1"
        }],
        bookCopies:{
          bookCopiesId: {
            branchId: 1,
            bookId: 1
          },
          branch:{
            id: 1,
            name: "Test Branch 1",
            address: "Test Address 1"
          },
          noOfCopies: 10
        }
      };
      spyOn(modalService, "open").and.returnValue(mockModalRef);
      mockModalRef.result = new Promise((resolve, reject) => reject("test error"));
      component.open("bookCopiesModal");
      tick();
      expect(component.closeResult).toBe("Dismissed")
    }));

    it('should editName', ()=>{
      component.editName = true;
      component.editNameForm();
      expect(component.editName).toBe(false);
    });

    it('should createAddForm',()=>{
      component.addBookForm = false;
      component.createAddForm();
      expect(component.addBookForm).toBe(true);
    })
});
