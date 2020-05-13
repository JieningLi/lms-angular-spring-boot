import { Component, OnInit, AfterViewInit } from "@angular/core";
import { LmsService } from "../../common/lms.service";
import { NgbModal, NgbModalRef } from "@ng-bootstrap/ng-bootstrap";
import { PagerService } from "../../common/pager.service";

@Component({
  selector: "app-author",
  templateUrl: "./author.component.html",
  styleUrls: ["./author.component.css"],
})
export class AuthorComponent implements OnInit, AfterViewInit {
  totalAuthors = 0;
  authors: any; //318
  books: any;
  selectedAuthor: any;
  private modalRef: NgbModalRef;
  errMsg: any;
  closeResult: any;
  pager: any = {};
  pagedAuthors: any[]; //10
  searchString = "";
  constructor(
    private lmsService: LmsService,
    private modalService: NgbModal,
    private pagerService: PagerService
  ) {}

  //Observables are promises which can be cancelled.
  //Observables also can return more than one value/object.
  //1> {{}} - expression
  //2> () - events
  //3> [()]="modelName"

  ngOnInit() {
    this.loadAuthors();
    this.loadBooks();
  }

  ngAfterViewInit() {}

  loadAuthors() {
    this.lmsService
      .getAll("http://localhost:8090/lms/readAuthors")
      .subscribe((resp) => {
        this.authors = resp;
        this.totalAuthors = this.authors.length;
        this.setPage(1);
      });
  }

  loadBooks() {
    this.lmsService
      .getAll("http://localhost:8090/lms/readBooks")
      .subscribe((resp) => {
        this.books = resp;
      });
  }

  addAuthor() {}

  deleteAuthor(authorId) {
    let author = {
      authorId: authorId,
    };
    this.lmsService
      .postObj("http://localhost:8090/lms/updateAuthor", author)
      .subscribe((res) => {
        this.lmsService
          .getAll("http://localhost:8090/lms/readAuthors")
          .subscribe((resp) => {
            this.authors = resp;
            this.totalAuthors = this.authors.length;
          });
      });
  }

  updateAuthor(author) {
    this.lmsService
      .postObj("http://localhost:8090/lms/updateAuthor", author)
      .subscribe((res) => {
        this.loadAuthors();
        this.modalService.dismissAll();
      });
  }

  open(content, obj) {
    this.selectedAuthor = obj;
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

  setPage(page: number) {
    if (page < 1 || page > this.pager.totalAuthors) {
      return;
    }
    this.pager = this.pagerService.getPager(this.totalAuthors, page, 10);
    this.pagedAuthors = this.authors.slice(
      this.pager.startIndex,
      this.pager.endIndex + 1
    );
  }

  searchAuthors() {
    this.lmsService
      .getAll(
        `http://localhost:8090/lms/readAuthorsByName/${this.searchString}`
      )
      .subscribe((resp) => {
        this.authors = resp;
        this.totalAuthors = this.authors.length;
        this.setPage(1);
      });
  }
}
