import { Component, OnInit, Input } from '@angular/core';
import {Router} from '@angular/router'

@Component({
  selector: 'app-borrower',
  templateUrl: './borrower.component.html',
  styleUrls: ['./borrower.component.css']
})
export class BorrowerComponent implements OnInit {

  @Input('search-book-form')
  searchBookForm: string;
  searchLoanForm: Int16Array;
  constructor(private router: Router) { }

  ngOnInit() {
  }

  searchBook(){
    this.router.navigate(['/lms/borrower/search-book'], {queryParams: {search: this.searchBookForm}});
  }

  searchLoan(){
    this.router.navigate(['lms/borrower/loan'], {queryParams: {cardNo: this.searchLoanForm}});
  }
}
