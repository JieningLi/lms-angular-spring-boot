import { Injectable } from '@angular/core';
import { logging } from 'protractor';

@Injectable({
  providedIn: 'root'
})
export class SearchService {

  constructor() { }
  searchMessage: string;
  searchInteger: Int16Array;

  changeMessage(message: string){
    this.searchMessage = message;
  }
  
  changeInteger(message: Int16Array){
    this.searchInteger = message;
  }
}
