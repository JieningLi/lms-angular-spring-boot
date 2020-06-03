import { fakeAsync, async, ComponentFixture, TestBed, tick } from '@angular/core/testing';

import { SearchBookComponent } from './search-book.component';
import { NgbModal, NgbModalRef, NgbModule } from "@ng-bootstrap/ng-bootstrap";
import { PagerService } from "src/app/common/pager.service";
import { LmsService } from "src/app/common/lms.service";
import { ActivatedRoute, Router, RouterModule } from "@angular/router";
import {RouterTestingModule} from '@angular/router/testing';
import { FormsModule } from '@angular/forms';
import { HttpClientModule, HttpErrorResponse } from '@angular/common/http';
import { Observable, of, throwError } from 'rxjs';
import { componentNeedsResolution } from '@angular/core/src/metadata/resource_loading';


//Mock Modal Ref Class
export class MockNgbModalRef{
  result: Promise<any> = new Promise((resolve, reject) => resolve("mock"));
}

describe('SearchBookComponent', () => {
  let component: SearchBookComponent;
  let fixture: ComponentFixture<SearchBookComponent>;
  let lmsService: LmsService;
  let modalService: NgbModal;
  let mockModalRef: MockNgbModalRef = new MockNgbModalRef();
  let pagerService: PagerService;
  let router: Router;
  let route: ActivatedRoute;
  
  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [FormsModule, NgbModule, RouterTestingModule.withRoutes([]), HttpClientModule],
      declarations: [ SearchBookComponent ],
      providers:[ LmsService, PagerService ]
    })
    .compileComponents();
    
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SearchBookComponent);
    router = TestBed.get(Router);
    lmsService = new LmsService(null);
    pagerService = new PagerService();
    modalService = TestBed.get(NgbModal);
    route = TestBed.get(ActivatedRoute);
    component = new SearchBookComponent(lmsService, modalService, pagerService, router, route);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
    
  });

  it('should load components and run life cycle', ()=> {
    spyOn(component, "loadBooks");
    spyOn(component, "loadAuthors");
    spyOn(component, "loadBranches");
    component.ngOnInit();
    //tests
    expect(component.loadBooks).toHaveBeenCalled;
    expect(component.loadAuthors).toHaveBeenCalled;
    expect(component.loadBranches).toHaveBeenCalled;
  });

  it('should load all books via a mock-service - return mock data', () => {
    const books = [{
      id: 1,
      title: "Test Book 1",
      authors: [{
        id: 1,
        name: "Test Author 1"},],
    },
    { id: 2,
      title: "Test Book 2",
      authors: [{
        id: 1,
        name: "Test Author 2"},],
    },
    { id: 3,
      title: "Test Book 3",
      authors: [{
        id: 2,
        name: "Test Author 3"},],
    },
    ]
    spyOn(lmsService, "getAll").and.returnValue(of(books));
    expect(lmsService).toBeTruthy();
    component.searchInput = "";
    component.loadBooks();
    expect(component.loadBooks).toHaveBeenCalled;
    expect(component.books).toEqual(books);
    expect(component.books.length).toEqual(3);
    expect(component.totalBooks).toEqual(3);
    expect(component.initialSearch).toHaveBeenCalled;
  });

  it('should load fail load books via a mock-service - return err message', () => {
    spyOn(lmsService,"getAll").and.returnValue(throwError({status:404}));
    component.loadBooks();
    expect(component.errMsg).toEqual("Cannot connect to server. Error at loading books. ");
  });

  it('should load all authors via a mock-service - return mock data', () => {
    const authors =[
      {
        id: 1,
        name: "Test Author 1"
      },
      {
        id: 2,
        name: "Test Author 2"
      }
    ]
    spyOn(lmsService, "getAll").and.returnValue(of(authors));
    component.searchInput = "";
    component.loadAuthors();
    expect(lmsService).toBeTruthy();
    expect(component.authors).toEqual(authors);
    expect(component.authors.length).toEqual(2);
  });

  it('should fail to load authors via a mock-service', () => {
    spyOn(lmsService,"getAll").and.returnValue(throwError({status:404}));
    component.loadAuthors();
    expect(component.errMsg).toEqual("Error at loading authors. ");
  });

  it('should load all branches via a mock-service - return mock data', () => {
    const branches =[
      {
        id: 1,
        branchName: "Test Branch 1",
        branchAddress: "Test Address 1"
      },
      {
        id: 2,
        branchName: "Test Branch 2",
        branchAddress: "Test Address 2",
      },
      {
        id: 3,
        branchName: "Test Branch 3",
        branchAddress: "Test Address 3"
      }
    ]
    spyOn(lmsService, "getAll").and.returnValue(of(branches));
    component.searchInput = "";
    component.loadBranches();
    expect(lmsService).toBeTruthy();
    expect(component.branches).toEqual(branches);
    expect(component.branches.length).toEqual(3);
  });

  it('should fail to load branches via a mock-service', () => {
    spyOn(lmsService,"getAll").and.returnValue(throwError({status:404}));
    component.loadBranches();
    expect(component.errMsg).toEqual("Error at loading branches. ");
  });

  it("'s initial search should work", ()=>{
    const param1 = "";
    const books = [{
      id: 1,
      title: "Test Book 1",
      authors: [{
        id: 1,
        name: "Test Author 1"}],
      },
      { id: 2,
        title: "Test Book 2",
        authors: [{
          id: 1,
          name: "Test Author 1"}],
      },
      { id: 3,
        title: "Testing Book 3",
        authors: [{
          id: 2,
          name: "Test Author 2"}],
      },
    ];
    component.books = books;
    let test = component.initialSearch(param1);
    expect(test).toEqual(books);

    let test2 = component.initialSearch("Test");
    expect(test2).toEqual(books);

    let test3 = component.initialSearch("Author");
    expect(test3).toEqual(books);
  });
  
  it("'s search should work with no input", ()=>{
    const param1 = "";
    const books = [{
      id: 1,
      title: "Test Book 1",
      authors: [{
        id: 1,
        name: "Test Author 1"}],
      },
      { id: 2,
        title: "Test Book 2",
        authors: [{
          id: 1,
          name: "Test Author 1"}],
      },
      { id: 3,
        title: "Testing Book 3",
        authors: [{
          id: 2,
          name: "Test Author 2"}],
      },
    ];
    component.books = books;
    let test = component.search(param1);
    test;
    expect(component.searchBooks).toEqual(books);
  });

  it("'s search should work with title input", ()=>{
    const param1 = "";
    const books = [{
      id: 1,
      title: "Test Book 1",
      authors: [{
        id: 1,
        name: "Test Author 1"}],
      },
      { id: 2,
        title: "Test Book 2",
        authors: [{
          id: 1,
          name: "Test Author 1"}],
      },
      { id: 3,
        title: "Testing Book 3",
        authors: [{
          id: 2,
          name: "Test Author 2"}],
      },
    ];
    component.books = books;

    component.search("Test");
    component.searchInput = "Test";
    expect(component.searchBooks).toEqual(books);
    expect(component.totalBooks).toEqual(3);

  });

  it("'s search should work with author input", ()=>{
    const param1 = "";
    const books = [{
      id: 1,
      title: "Test Book 1",
      authors: [{
        id: 1,
        name: "Test Author 1"}],
      },
      { id: 2,
        title: "Test Book 2",
        authors: [{
          id: 1,
          name: "Test Author 1"}],
      },
      { id: 3,
        title: "Testing Book 3",
        authors: [{
          id: 2,
          name: "Test Author 2"}],
      },
    ];
    component.books = books;
    component.search("Author");
    component.searchInput = "Author";
    expect(component.searchBooks).toEqual(books);
   
  });

  it("should open modal", fakeAsync(()=>{
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
    component.open("rentBookModal", mockBook);
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
    component.open("rentBookModal", mockBook);
    tick();
    expect(component.closeResult).toBe("Dismissed")
  }));

  it("should reserve book working intended", ()=> {
    const mockBookCopy = {
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
      };
    const mockBook = {
      id: 1,
      title: "Test Book 1",
      authors:[{
        id: 1,
        name: "Test Author 1"
      }],
      bookCopies: [mockBookCopy]
    }
    component.cardNo = 1;
    spyOn(lmsService, "putObj").and.returnValue(of(mockBookCopy));
    spyOn(lmsService, "postObj").and.returnValue(of(mockBook));
    component.selectedBranch = mockBookCopy;
    component.selectedBook = mockBook;
    component.reserveBook();
    expect(modalService.dismissAll).toHaveBeenCalled;
  });

  it("should reserve book - putObj not working", ()=> {
    const mockBookCopy = {
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
      };
    const mockBook = {
      id: 1,
      title: "Test Book 1",
      authors:[{
        id: 1,
        name: "Test Author 1"
      }],
      bookCopies: [mockBookCopy]
    }
    component.selectedBranch = mockBookCopy;
    component.selectedBook = mockBook;
    component.cardNo = 1;
    spyOn(lmsService, "putObj").and.returnValue(throwError({status: 400}));
    component.reserveBook();
    expect(component.errMsg).toBe("Reserve Failed. Try Again.");
    expect(component.connection).toBe(false);
  });

  it("should reserve book - postObj not working", ()=> {
    const mockBookCopy = {
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
      };
    const mockBook = {
      id: 1,
      title: "Test Book 1",
      authors:[{
        id: 1,
        name: "Test Author 1"
      }],
      bookCopies: [mockBookCopy]
    }
    component.selectedBranch = mockBookCopy;
    component.selectedBook = mockBook;
    component.cardNo = 1;
    spyOn(lmsService, "putObj").and.returnValue(of(mockBookCopy));
    spyOn(lmsService, "postObj").and.returnValue(throwError({status: 404}));
    component.reserveBook();
    expect(component.errMsg).toBe("Create Loan Failed");
    expect(component.connection).toBe(false);
  });

  it('setPage should return none if page = 0', ()=>{
    const setPager = component.setPage(0);
    expect(setPager).toBe(undefined);
  });
});
