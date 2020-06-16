import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { NgbModal, NgbModalRef, NgbModule } from "@ng-bootstrap/ng-bootstrap";
import { PagerService } from "src/app/common/pager.service";
import { LmsService } from "src/app/common/lms.service";
import { ActivatedRoute, Router, RouterModule } from "@angular/router";
import {RouterTestingModule} from '@angular/router/testing';
import { FormsModule } from '@angular/forms';
import { HttpClientModule, HttpErrorResponse,  } from '@angular/common/http';
import { of, throwError } from 'rxjs';
import { UpdateLoanComponent } from './update-loan.component';

describe('UpdateLoanComponent', () => {
  let component: UpdateLoanComponent;
  let fixture: ComponentFixture<UpdateLoanComponent>;
  let lmsService: LmsService;
  let modalService: NgbModal;
  let pagerService: PagerService;
  let router: Router;
  let route: ActivatedRoute;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [FormsModule, NgbModule, RouterTestingModule.withRoutes([]), HttpClientModule],
      declarations: [ UpdateLoanComponent ],
      providers: [LmsService, PagerService]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UpdateLoanComponent);
    router = TestBed.get(Router);
    lmsService = new LmsService(null);
    pagerService = new PagerService();
    modalService = TestBed.get(NgbModal);
    route = TestBed.get(ActivatedRoute);
    component = new UpdateLoanComponent(lmsService, modalService, pagerService, router, route);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should load components and run life cycle', ()=> {
    spyOn(component, "loadBookLoans");
    component.ngOnInit();
    expect(component.loadBookLoans).toHaveBeenCalled;
  });

  it('should load all book loans', ()=>{
    const mockBookLoans = [{
      bookLoansId:{
        branchId: 1,
        bookId: 1,
        cardNo: 1
      },
      book:{
        id: 1,
        title: "Test Book 1",
        authors: [{
          id: 1,
          name: "Test Author 1"
        }],
      },
      branch:{
        id: 1,
        branchName: "Test Branch 1",
        branchAddress: "Test Address 1"
      }
    },
    {
      bookLoansId:{
        branchId: 1,
        bookId: 3,
        cardNo: 1
      },
      book:{
        id: 3,
        title: "Test Book 3",
        authors: [{
          id: 2,
          name: "Test Author 2"
        }],
      },
      branch:{
        id: 1,
        branchName: "Test Branch 1",
        branchAddress: "Test Address 1"
      }
    },
    {
      bookLoansId:{
        branchId: 1,
        bookId: 1,
        cardNo: 1
      },
      book:{
        id: 1,
        title: "Test Book 1",
        authors: [{
          id: 1,
          name: "Test Author 1"
        }],
      },
      branch:{
        id: 1,
        branchName: "Test Branch 1",
        branchAddress: "Test Address 1"
      }
    },
    ];
    spyOn(lmsService, "getAll").and.returnValue(of(mockBookLoans));
    component.loadBookLoans();
    expect(component.connection).toBe(true);
    expect(component.bookLoans).toBe(mockBookLoans);
    expect(component.setPage).toHaveBeenCalled;
  });

  it('should fail load book loans and return error', ()=> {
    spyOn(lmsService, "getAll").and.returnValue(throwError({status: 404}));
    component.loadBookLoans();
    expect(component.connection).toBe(false);
    expect(component.errMsg).toBe("Cannot connect to server. Error at loading books. ");
  });

  it('should setPage 0 return undefined', ()=> {
    const set = component.setPage(0);
    expect(set).toBe(undefined);
  })
});

