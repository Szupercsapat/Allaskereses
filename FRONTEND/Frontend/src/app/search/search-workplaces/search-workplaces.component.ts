import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs';
import { GetPagedJobsService } from './search-jobs.service';
import { Job } from 'src/app/entity/job.model';

@Component({
  selector: 'app-search-workplaces',
  templateUrl: './search-workplaces.component.html',
  styleUrls: ['./search-workplaces.component.css']
})
export class SearchWorkplacesComponent implements OnInit, OnDestroy {

  private sub: Subscription;
  private sub2: Subscription;
  private sub3: Subscription;

  private page = 0;

  private maxJobs;

  private maxPages;

  public jobs: Job[] = [new Job('', '', '', [])];

  public IDs: number[] = [];

  constructor(
    public getPagedService: GetPagedJobsService,
  ) {}

  ngOnInit() {
    this.sub = new Subscription();
    this.sub = this.getPagedService.getJobsCount().subscribe(
      response => {
        // console.log(response);
        const obj = JSON.stringify(response);
        const obj2 = JSON.parse(obj);
        // console.log(obj2);
        this.maxJobs = JSON.parse(obj2[Object.keys(obj2)[0]]);
        this.maxPages = Math.ceil(this.maxJobs / 5);
      },
      error => {
        console.log(error);
      }
    );
    this.sub2 = new Subscription();
    this.sub2 = this.getPagedService.getPagedJobs(this.page).subscribe(
      (response) => {
        // console.log(response);
        const obj = JSON.stringify(response);
        const obj2 = JSON.parse(obj);
        const obj3: any[] = Array.of(JSON.parse(obj2[Object.keys(obj2)[0]]));
        const obj4 = obj3[0];
        this.getPagedService.getData(obj4);
      },
      error => console.log(error),
      () => {
        this.jobs = this.getPagedService.jobs;
        this.IDs = this.getPagedService.IDs;
        // console.log(this.IDs);
      }
    );
    this.sub3 = new Subscription();
    /*this.sub3 = this.getPagedService.getCategoryName(3).subscribe(
      response => console.log(response)
    );*/
  }

  previousPage() {
    if (this.page > 0) {
      this.page--;
      this.ngOnInit();
    }
  }

  nextPage() {
    if (this.page < this.maxPages - 1) {
      this.page++;
      this.ngOnInit();
    }
  }

  ngOnDestroy() {
    this.sub.unsubscribe();
    this.sub2.unsubscribe();
    this.sub3.unsubscribe();
  }

}
