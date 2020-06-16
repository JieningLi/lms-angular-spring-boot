import { TestBed, getTestBed } from '@angular/core/testing';

import { LmsService } from './lms.service';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

describe('LmsService', () => {
  let service: LmsService;
  let httpMock: HttpTestingController;
  let injector: TestBed; 
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [LmsService]
    });

    injector = getTestBed();
    service = injector.get(LmsService);
    httpMock = injector.get(HttpTestingController);
  });

  afterEach(()=>{
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('getAll return data', ()=>{
    service.getAll("https://run.mocky.io/v3/8128f5f3-453d-44cb-a619-6ae2886df453").subscribe(
      (res) => {
        expect(res).toEqual({message:"getAll works"});
      }
    );

    const req = httpMock.expectOne((req)=> req.method === 'GET' && req.url === 'https://run.mocky.io/v3/8128f5f3-453d-44cb-a619-6ae2886df453');
    expect(req.request.method).toBe('GET');
  });

  it('postObj return data', ()=>{
    const obj= {"message": "object1"};
    let headers = new Headers();
    headers.append("Content-Type", "application/json");
    service.postObj("https://run.mocky.io/v3/bb78674f-d667-4dba-a920-fb8df9f465e6", obj, headers).subscribe(
      (res) => {
        expect(res).toEqual({message:"postObj works"});
      }
    );

    const req = httpMock.expectOne((req)=> req.method === 'POST' && req.url === "https://run.mocky.io/v3/bb78674f-d667-4dba-a920-fb8df9f465e6");
    expect(req.request.method).toBe('POST');
  });

  it('deleteObj return no content', ()=>{

    service.deleteObj("https://run.mocky.io/v3/bb78674f-d667-4dba-a920-fb8df9f465e6").subscribe(
      (res) => {
        expect(res).toEqual({message:"deleteObj works"});
      }
    );

    const req = httpMock.expectOne((req)=> req.method === 'DELETE' && req.url === "https://run.mocky.io/v3/bb78674f-d667-4dba-a920-fb8df9f465e6");
    expect(req.request.method).toBe('DELETE');
  });
});
