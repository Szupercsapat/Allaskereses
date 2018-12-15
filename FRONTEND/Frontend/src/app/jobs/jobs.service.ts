import { Injectable } from '@angular/core';
import { Http, Headers, Response } from '@angular/http';
import { Observable } from 'rxjs';
import { Job } from '../entity/job.model';

@Injectable()
export class JobsService {

  constructor(
    private http: Http
  ) {}

  sendUpload(body: Job) {
    const url = 'http://localhost:8080/rft/job';
    const header = new Headers();
    header.append('Content-Type', 'application/json');

    return this.http.post(url, body, {headers: header});
  }
}
