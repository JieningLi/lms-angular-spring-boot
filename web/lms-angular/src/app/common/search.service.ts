import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class SearchService {

  constructor() { }
  searchMessage = '';
  searchInteger: Int16Array;

  changeMessage(message: string){
    this.searchMessage = message;
  }
  
  changeInteger(message: Int16Array){
    this.searchInteger = message;
  }
}
