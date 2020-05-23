import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders, HttpResponse } from "@angular/common/http";

@Injectable({
  providedIn: "root",
})
export class LmsService {
  constructor(private http: HttpClient) {}

  getAll(url) {
    return this.http.get(url);
  }

  postObj(url, obj, obj2) {
    return this.http.post(url, obj, obj2);
  }

  deleteObj(url) {
    return this.http.delete(url);
  }

  putObj(url, obj, obj2) {
    return this.http.put(url, obj, obj2);
  }

  // putObj(url, obj){
  //   return this.http.put(url, obj);
  // }
}
