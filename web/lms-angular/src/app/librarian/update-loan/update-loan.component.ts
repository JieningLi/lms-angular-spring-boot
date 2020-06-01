import { Component, OnInit } from '@angular/core';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { PagerService } from 'src/app/common/pager.service';
import { LmsService } from 'src/app/common/lms.service';
import { ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'app-update-loan',
  templateUrl: './update-loan.component.html',
  styleUrls: ['./update-loan.component.css']
})
export class UpdateLoanComponent implements OnInit {

  constructor(
    private lmsService: LmsService,
    private modalService: NgbModal,
    private pagerService: PagerService,
    private router: Router,
    private route: ActivatedRoute) { }

  searchInput: Int16Array;
  bookLoans: any;
  totalLoans: any;
  connection: any;
  errMsg: any;
  pager: any={};
  pagedLoans: any[]
  
  ngOnInit() {
    this.route.queryParams
    .subscribe(params =>{
      this.searchInput = params.cardNo;
    });
    this.loadBookLoans();
  }

  loadBookLoans(){
    this.lmsService
    .getAll(`http://localhost:8082/lms/librarian/bookloans/${this.searchInput}`)
    .subscribe(
      (resp)=>{
       this.connection = true;
       this.bookLoans = resp;
       this.totalLoans = this.bookLoans.length;
       this.setPage(1);
       console.log(this.bookLoans);
      },
      (error)=>{
        this.errMsg = "Cannot connect to server. Error at loading books. "
        this.connection = false;
      });
  }

  setPage(page: number){
    if(page < 1 || page > this.pager.totalLoans){
      return;
    }
    this.pager = this.pagerService.getPager(this.totalLoans, page, 10);
    if(this.bookLoans){
      this.pagedLoans = this.bookLoans.slice(
        this.pager.startIndex,
        this.pager.endIndex + 1
      );
    }
  }
}
