import { Component, OnInit, AfterViewInit } from "@angular/core";
import { LmsService } from "../../common/lms.service";

@Component({
  selector: "app-author",
  templateUrl: "./author.component.html",
  styleUrls: ["./author.component.css"],
})
export class AuthorComponent implements OnInit, AfterViewInit {
  totalAuthors = 0;
  authors: any;
  constructor(private lmsService: LmsService) {}

  ngOnInit() {
    this.loadAuthors();
  }

  ngAfterViewInit() {}

  loadAuthors() {
    this.lmsService
      .getAll("http://localhost:8090/lms/readAuthors")
      .subscribe((resp) => {
        this.authors = resp;
        this.totalAuthors = this.authors.length;
      });
    //Observables are promises which can be cancelled.
    //Observables also can return more than one value/object.
  }
}
