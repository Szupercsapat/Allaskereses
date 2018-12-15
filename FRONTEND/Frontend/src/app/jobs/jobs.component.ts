import { Component, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { CookieService } from 'ngx-cookie-service';
import { Job } from '../entity/job.model';
import { Subscription } from 'rxjs';
import { Router, ActivatedRoute, Params } from '@angular/router';

@Component({
  selector: 'app-jobs',
  templateUrl: './jobs.component.html',
  styleUrls: ['./jobs.component.css']
})
export class JobsComponent implements OnInit {

  @ViewChild('f') signupForm: NgForm;

  private sub: Subscription;
  private paramsSubscription: Subscription;


  private job: {
    id: number,
    username: string
  };

  constructor(
    // private jobService: JobsService,
    private cookieService: CookieService,
    private router: Router,
    private route: ActivatedRoute,
  ) {}

  ngOnInit() {
    this.sub = new Subscription();
    this.paramsSubscription = new Subscription();
    this.job = {
      id: this.route.snapshot.params['id'],
      username: ''
    };
    this.paramsSubscription = this.route.params
      .subscribe(
        (params: Params) => {
          this.job.id = params['id'];
        }
      );
  }

  /*onUpload() {
    const job = new Job(
      this.cookieService.get('USERNAME'),
      this.signupForm.value.userData.name,
      this.signupForm.value.userData.desc
    );
    this.sub = this.jobService.sendUpload(job).subscribe(
      response => { console.log(response); },
      error => { console.log(error); }
    );
  }*/

}
