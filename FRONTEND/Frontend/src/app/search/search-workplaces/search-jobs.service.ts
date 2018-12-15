import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs';
import { Profile } from 'src/app/entity/profile.model';
import { Job } from 'src/app/entity/job.model';

@Injectable()
export class GetPagedJobsService {

  public jobs: Job[] = [];
  public IDs: number[] = [];

  constructor(private http: Http) {}

  getData(data: Job[]) {
    const length = data.length;
    console.log(data);
    this.jobs = [];
    this.IDs = [];

    for (let i = 0; i < length; i++) {
        const prof = new Job(
          data[i].username, data[i].name, data[i].description, data[i].categories
        );
        this.IDs.push(data[i][Object.keys(data[i])[0]]);
        this.jobs.push(prof);
    }
  }

  getCategoryName(id: number) {
    const url = 'http://localhost:8080/rft/jobCategories/search/findByIdIn?ids=' + id;
    return this.http.get(url);
  }

  getPagedJobs(page: number) {
    const url = 'http://localhost:8080/rft/job/getAll/page/' + page + '/size/5';
    return this.http.get(url);
  }

  getJobsCount() {
    const url = 'http://localhost:8080/rft/job/getAllCount';
    return this.http.get(url);
  }
}
