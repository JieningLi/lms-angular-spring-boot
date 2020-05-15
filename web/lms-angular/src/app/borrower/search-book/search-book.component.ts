import { Component, OnInit } from '@angular/core';
import { SearchService } from 'src/app/common/search.service';

@Component({
  selector: 'app-search-book',
  templateUrl: './search-book.component.html',
  styleUrls: ['./search-book.component.css']
})
export class SearchBookComponent implements OnInit {

  constructor(private searchService: SearchService) { }
  searchInput: string;

  ngOnInit() {
    this.searchInput = this.searchService.searchMessage;
  }

}
