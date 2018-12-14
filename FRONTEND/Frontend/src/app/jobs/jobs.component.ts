import { Component, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { JobsService } from './jobs.service';
import { CookieService } from 'ngx-cookie-service';
import { Job } from '../entity/job.model';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-jobs',
  templateUrl: './jobs.component.html',
  styleUrls: ['./jobs.component.css']
})
export class JobsComponent implements OnInit {

  @ViewChild('f') signupForm: NgForm;

  private sub: Subscription;

  constructor(
    private jobService: JobsService,
    private cookieService: CookieService
  ) {}

  ngOnInit() {
    this.sub = new Subscription();
  }

  onUpload() {
    const job = new Job(
      this.cookieService.get('USERNAME'),
      this.signupForm.value.userData.name,
      this.signupForm.value.userData.desc
    );
    this.sub = this.jobService.sendUpload(job).subscribe(
      response => { console.log(response); },
      error => { console.log(error); }
    );
  }

}
