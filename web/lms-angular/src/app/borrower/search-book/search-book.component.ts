import { Component, OnInit, AfterContentChecked } from '@angular/core';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { PagerService } from 'src/app/common/pager.service';
import { LmsService } from 'src/app/common/lms.service';
import { ActivatedRoute, Router} from '@angular/router'

@Component({
  selector: 'app-search-book',
  templateUrl: './search-book.component.html',
  styleUrls: ['./search-book.component.css']
})
export class SearchBookComponent implements OnInit  {

  constructor(
    private lmsService: LmsService,
    private modalService: NgbModal,
    private pagerService: PagerService,
    private router: Router,
    private route: ActivatedRoute
    ) { }
  
  //for search input
  searchInput: any;
  //total books searched
  totalBooks = 0;
  //array of books picked
  books: any;
  searchBooks: any;
  selectedBook: any;
  authors: any;
  branches: any;
  private modalRef: NgbModalRef;
  errMsg: any;
  closeResult: any;
  pager:any = {};
  pagedBooks: any[]
  connection: boolean;
  selectedBranch: any;
  cardNo: any;
  
  ngOnInit() {
    this.route.queryParams
      .subscribe(params =>{
        this.searchInput = params.search;
      });
    this.loadBooks();
    this.loadAuthors();
    this.loadBranches();
  }
 
  loadBooks(){
    this.lmsService
      .getAll("http://localhost:8083/lms/borrower/books")
      .subscribe(
        (resp)=>{
          this.connection = true;
          this.books = resp;
          this.searchBooks = this.initialSearch(this.searchInput);
          this.totalBooks = this.searchBooks.length;
          this.setPage(1);
        },
        (error)=>{
          this.errMsg = "Cannot connect to server. Error at loading books. "
          this.connection = false;
        });
  }

  loadAuthors(){
    this.lmsService
      .getAll("http://localhost:8083/lms/borrower/authors")
      .subscribe((resp)=>{
        this.authors = resp;
      },(error)=>{
        this.errMsg += "Error at loading authors. "
        this.connection = false;
      })
  }

  loadBranches(){
    this.lmsService
    .getAll("http://localhost:8083/lms/borrower/branches")
    .subscribe((resp)=>{
      this.branches = resp;
    },(error)=>{
      this.errMsg += "Error at loading branches. "
      this.connection = false;
    })
  }

  reserveBook(){
    this.selectedBranch.noOfCopies--;
    let headers = new Headers();
    headers.append("Content-Type", "application/json");
    this.lmsService
      .putObj("http://localhost:8083/lms/borrower/bookcopies:bookcopies", this.selectedBranch, {headers: headers})
      .subscribe((res) => {
        this.modalService.dismissAll();
      },
      (err)=>{
        this.errMsg = "Reserve Failed. Try Again."
        this.connection = false;
      });
  
  }
  //SEARCH INPUT
  initialSearch(input: string){
    if(input.length === 0)
    {
      return this.books;
    }
    let newBooks = new Array();
    this.books.forEach(element => {
        if(element.title.toLowerCase().includes(input.toLowerCase())){
          newBooks.push(element);
        }
        else{
          element.authors.forEach(author => {
            if(author.name.toLowerCase().includes(input.toLowerCase()))
              newBooks.push(element);
          })
        }
      }
    )

    return newBooks;
  }
  search(input: string){
    if(input.length === 0)
      this.searchBooks = this.books;
    let newBooks = new Array();
    this.books.forEach(element => {
        if(element.title.toLowerCase().includes(input.toLowerCase())){
          newBooks.push(element);
        }
        else{
          element.authors.forEach(author => {
            if(author.name.toLowerCase().includes(input.toLowerCase()))
              newBooks.push(element);
          })
        }
      }
    )
    this.searchBooks = newBooks;
    this.totalBooks = this.searchBooks.length;
    this.router.navigate(['/lms/borrower/search-book'], {queryParams: {search: this.searchInput}});
    this.setPage(1);
  }

  //
  open(content, obj){
    this.selectedBook = obj;
    this.modalRef = this.modalService.open(content);
    this.modalRef.result.then(
      (result) => {
        this.errMsg = "";
        this.closeResult = `Closed with ${result}`;
      },
      (reason)=>{
        this.errMsg = "";
        this.closeResult = `Dismissed`;
      }
    )
  }

  setPage(page: number){
    if(page < 1 || page > this.pager.totalBooks){
      return;
    }
    this.pager = this.pagerService.getPager(this.totalBooks, page, 10);
    if(this.searchBooks){
      this.pagedBooks = this.searchBooks.slice(
        this.pager.startIndex,
        this.pager.endIndex + 1
      );
    }
  }

 
}
