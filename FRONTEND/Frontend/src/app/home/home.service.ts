import { Injectable } from '@angular/core';
import { Http, Headers, Response } from '@angular/http';

@Injectable()
export class HomeService {

  constructor(
    private http: Http
  ) {}

  getAllCategories() {
    const url = 'http://localhost:8080/rft/jobCategories';

    return this.http.get(url);
  }
}
