import { Component, OnInit, Input } from '@angular/core';
import {Router} from '@angular/router'
import { SearchService } from '../common/search.service';
@Component({
  selector: 'app-borrower',
  templateUrl: './borrower.component.html',
  styleUrls: ['./borrower.component.css']
})
export class BorrowerComponent implements OnInit {

  @Input('search-book-form')
  searchBookForm: string;
  searchLoanForm: Int16Array;
  constructor(private searchService: SearchService, private router: Router) { }

  ngOnInit() {
  }

  searchBook(){
    this.searchService.changeMessage(this.searchBookForm);
    this.router.navigate(['/lms/borrower/search-book']);
  }

  searchLoan(){
    this.searchService.changeInteger(this.searchLoanForm);
    this.router.navigate(['lms/borrower/loan'])
  }
}
