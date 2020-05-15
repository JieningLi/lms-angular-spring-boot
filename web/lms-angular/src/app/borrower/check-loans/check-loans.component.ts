import { Component, OnInit } from '@angular/core';
import { SearchService } from 'src/app/common/search.service';

@Component({
  selector: 'app-check-loans',
  templateUrl: './check-loans.component.html',
  styleUrls: ['./check-loans.component.css']
})
export class CheckLoansComponent implements OnInit {

  constructor(private searchService: SearchService) { }
  searchInput: Int16Array;

  ngOnInit() {
    this.searchInput = this.searchService.searchInteger;
  }

}
