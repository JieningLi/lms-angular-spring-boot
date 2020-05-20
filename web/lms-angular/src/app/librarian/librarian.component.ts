import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-librarian',
  templateUrl: './librarian.component.html',
  styleUrls: ['./librarian.component.css']
})
export class LibrarianComponent implements OnInit {


  searchLibraryForm: Int16Array;
  searchCardNoForm: Int16Array;
  constructor(private router: Router) { }

  ngOnInit() {
  }

  searchLibrary(){
    this.router.navigate(['/lms/librarian/library'], {queryParams: {branchNo: this.searchLibraryForm}});
  }

  searchLoan(){
    this.router.navigate(['/lms/librarian/loan'], {queryParams: {cardNo: this.searchCardNoForm}});
  }
}
