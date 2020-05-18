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
  authorName: any;
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

  ngOnInit() {
    this.loadAuthors();
    this.loadBooks();
  }

  ngAfterViewInit() {}

  loadAuthors() {
    this.lmsService
      .getAll("http://localhost:8081/lms/admin/authors")
      .subscribe((resp) => {
        this.authors = resp;
        this.totalAuthors = this.authors.length;
        this.setPage(1);
      });
  }

  loadBooks() {
    this.lmsService
      .getAll("http://localhost:8081/lms/admin/books")
      .subscribe((resp) => {
        this.books = resp;
      });
  }

  addAuthor(authorName) {
    let headers = new Headers();
    headers.append("Content-Type", "application/json");
    this.lmsService
      .postObj(
        "http://localhost:8081/lms/admin/authors/",
        { authorName: authorName },
        { headers: headers }
      )
      .subscribe((res) => {
        this.loadAuthors();
        this.modalService.dismissAll();
      });
  }

  deleteAuthor(authorId) {
    let author = {
      authorId: authorId,
    };
    this.lmsService
      .deleteObj("http://localhost:8081/lms/admin/authors/" + authorId)
      .subscribe((res) => {
        this.lmsService
          .getAll("http://localhost:8081/lms/admin/authors")
          .subscribe((resp) => {
            this.authors = resp;
            this.totalAuthors = this.authors.length;
          });
      });
  }

  updateAuthor() {
    let author = {
      authorId: this.selectedAuthor.authorId,
      authorName: this.selectedAuthor.authorName,
    };
    let headers = new Headers();
    headers.append("Content-Type", "application/json");
    this.lmsService
      .putObj(
        "http://localhost:8081/lms/admin/authors/" + author.authorId,
        { authorName: author.authorName },
        { headers: headers }
      )
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

  openAdd(content) {
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

  searchAuthors(searchString) {
    console.log(searchString);
    this.lmsService
      .getAll(
        `http://localhost:8081/lms/admin//authors/search/${this.searchString}`
      )
      .subscribe((resp) => {
        this.authors = resp;
        this.totalAuthors = this.authors.length;
        this.setPage(1);
      });
  }
}
