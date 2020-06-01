import { Component, OnInit } from '@angular/core';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { PagerService } from 'src/app/common/pager.service';
import { LmsService } from 'src/app/common/lms.service';
import { ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'app-library',
  templateUrl: './library.component.html',
  styleUrls: ['./library.component.css']
})
export class LibraryComponent implements OnInit {

  constructor(
    private lmsService: LmsService,
    private modalService: NgbModal,
    private pagerService: PagerService,
    private router: Router,
    private route: ActivatedRoute
  ) { }

  searchInput: string;
  library: any;
  books: any;
  connection: any;
  errMsg: string;

  private modalRef: NgbModalRef;
  closeResult: any;
  bookCopies: any;
  booksInLibrary: Array<any> = [];
  booksNotInLibrary: any;
  
  selectedBook: any;
  
  nameInput: string;
  addressInput: string;
  selectedCopies: any;
  noOfCopiesInput: Int16Array;
  editName: boolean;
  editAddress: boolean;
  editNoOfCopies: boolean;
  editRowIndex: Int16Array;

  addBookForm: boolean;

  ngOnInit() {
    this.route.queryParams
      .subscribe(params =>{
        this.searchInput = params.branchNo;
      });
    this.loadLibrary();
    this.loadBookCopies();
    this.addBookForm = false;
    document.body.classList.add('bg-img');
  }

  loadLibrary(){
    this.lmsService
      .getAll(`http://localhost:8082/lms/librarian/branch/${this.searchInput}`)
      .subscribe(
        (resp)=>{
          this.connection = true;
          this.library = resp;
          this.nameInput = this.library.name;
          this.addressInput = this.library.address;
          this.editName = false;
          this.editAddress = false;
        },
        (error)=>{
          this.errMsg = "Cannot connect to server. Error at loading branch. "
          this.connection = false;
        });
  }

  loadUnlistedBooks(){
    this.lmsService
      .getAll(`http://localhost:8082/lms/librarian/unlistedbooks/${this.searchInput}`)
      .subscribe(
        (resp)=>{
          this.connection=true;
          this.booksNotInLibrary = resp;
        },
        (err) =>{
          this.errMsg = err.error.message;
        }
      )
  }
  loadBookCopies(){
    this.lmsService
      .getAll(`http://localhost:8082/lms/librarian/bookcopies/${this.searchInput}`)
      .subscribe(
        (resp)=>{
          this.connection = true;
          this.bookCopies=resp;
          this.bookCopies.forEach(bc => {
            this.booksInLibrary.push(bc.book);
          });
          this.loadUnlistedBooks();
        },
        (error)=>{
          this.errMsg = "Cannot connect to server. Error at loading book copies. "
          this.connection = false;
        });

  }

  updateLibrary(){
    let newLibrary = {
      id: this.library.id,
      name: this.nameInput,
      address: this.addressInput,
    }
    let headers= new Headers();
    headers.append("Content-Type", "application/json");
    this.lmsService
      .putObj(`http://localhost:8082/lms/librarian/branch`, newLibrary, {headers: headers})
      .subscribe(
        (resp)=>{
          this.loadLibrary();
          this.modalService.dismissAll();
        },
        (error)=>{
          this.errMsg = "Error at updating branch. "
          this.connection = false;
        });
  }

  addBookCopy(){
    let newCopies= {
      bookCopiesId: {
        bookId: this.selectedBook.id,
        branchId: this.library.id,
      },
      noOfCopies:this.noOfCopiesInput,
      book: this.selectedBook,
      branch: this.library
    }
    let headers= new Headers();
    headers.append("Content-Type", "application/json");
    this.lmsService
      .postObj(`http://localhost:8082/lms/librarian/bookcopies`, newCopies, {headers: headers})
      .subscribe((resp)=>{
        this.loadBookCopies();
      });
    this.addBookForm = false;

  }

  updateBookCopy(){
    this.selectedCopies.noOfCopies = this.noOfCopiesInput;
    let newCopies = {
      bookCopiesId: this.selectedCopies.bookCopiesId,
      noOfCopies: this.noOfCopiesInput,
      book: this.selectedCopies.book,
      branch: this.selectedCopies.branch
    }
    let headers= new Headers();
    headers.append("Content-Type", "application/json");
    this.lmsService
      .putObj(`http://localhost:8082/lms/librarian/bookcopies`, newCopies , {headers: headers})
      .subscribe(
        (resp)=>{
          this.loadBookCopies();
        },
        (error)=>{
          this.errMsg = error.error.message;
          this.connection = false;
        });
        this.closeEditRow();
  }

  editRow(copy, index){
    this.selectedCopies = copy;
    this.editRowIndex = index;
    this.editNoOfCopies = true;
    this.noOfCopiesInput = copy.noOfCopies;
    console.log(this.editRowIndex);
    console.log(this.selectedCopies);
  }
  open(content) {
    this.modalRef = this.modalService.open(content);
    this.modalRef.result.then(
      (result) => {
        this.errMsg = "";
        this.closeResult = `Closed with ${result}`;
      },
      (reason) => {
        this.errMsg = "";
        this.closeResult = `Dismissed`;
      }
    );
  }
  closeEditRow(){
    this.editNoOfCopies = false;
  }
  editNameForm(){
    this.editName = !this.editName;
  }

  createAddForm(){
    this.addBookForm = !this.addBookForm;
  }

}
