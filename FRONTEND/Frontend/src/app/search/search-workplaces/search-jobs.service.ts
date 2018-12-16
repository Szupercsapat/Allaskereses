import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs';
import { Profile } from 'src/app/entity/profile.model';
import { Job } from 'src/app/entity/job.model';
import { Job2 } from 'src/app/entity/job2.model';

@Injectable()
export class GetPagedJobsService {

  public jobs: Job[] = [];
  public IDs: number[] = [];
  public offererIds: number[] = [];

  constructor(private http: Http) {}

  getData(data: Job2[]) {
    const length = data.length;
    console.log(data);
    this.jobs = [];
    this.IDs = [];
    this.offererIds = [];

    for (let i = 0; i < length; i++) {
        const prof = new Job2(
          data[i].username, data[i].name, data[i].description, data[i].categories, data[i].offererId
        );
        this.IDs.push(data[i][Object.keys(data[i])[0]]);
        this.jobs.push(prof);
        this.offererIds.push(data[i].offererId);
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
