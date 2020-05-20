import { Component, OnInit } from '@angular/core';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { PagerService } from 'src/app/common/pager.service';
import { LmsService } from 'src/app/common/lms.service';
import { ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'app-library',
  templateUrl: './library.component.html',
  styleUrls: ['./library.component.css']
})
export class LibraryComponent implements OnInit {

  constructor(
    private lmsService: LmsService,
    private modalService: NgbModal,
    private pagerService: PagerService,
    private router: Router,
    private route: ActivatedRoute
  ) { }

  searchInput: string;
  library: any;
  connection: any;
  errMsg: string;
  nameInput: string;
  addressInput: string;
  editName: boolean;
  editAddress: boolean;
  private modalRef: NgbModalRef;
  closeResult: any;
  bookCopies: any;


  ngOnInit() {
    this.route.queryParams
      .subscribe(params =>{
        this.searchInput = params.branchNo;
      });
    this.loadLibrary();
    this.loadBookCopies();
    document.body.classList.add('bg-img');
  }

  loadLibrary(){
    this.lmsService
      .getAll(`http://localhost:8082/lms/librarian/branch/${this.searchInput}`)
      .subscribe(
        (resp)=>{
          this.connection = true;
          this.library = resp;
          this.nameInput = this.library.name;
          this.addressInput = this.library.address;
          this.editName = false;
          this.editAddress = false;
        },
        (error)=>{
          this.errMsg = "Cannot connect to server. Error at loading branch. "
          this.connection = false;
        });
  }

  loadBookCopies(){
    this.lmsService
      .getAll(`http://localhost:8082/lms/librarian/bookcopies/${this.searchInput}`)
      .subscribe(
        (resp)=>{
          this.connection = true;
          this.bookCopies=resp;
        },
        (error)=>{
          this.errMsg = "Cannot connect to server. Error at loading book copies. "
          this.connection = false;
        });
  }
  updateLibrary(){
    let newLibrary = {
      id: this.library.id,
      name: this.nameInput,
      address: this.addressInput,
    }
    let headers= new Headers();
    headers.append("Content-Type", "application/json");
    this.lmsService
      .putObj(`http://localhost:8082/lms/librarian/branch`, newLibrary, {headers: headers})
      .subscribe(
        (resp)=>{
          this.loadLibrary();
          this.modalService.dismissAll();
        },
        (error)=>{
          this.errMsg = "Error at updating branch. "
          this.connection = false;
        });
  }

  open(content) {
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
  editNameForm(){
    this.editName = !this.editName;
  }

}
