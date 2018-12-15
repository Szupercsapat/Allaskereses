import { Component, OnInit, ViewChild, OnDestroy } from '@angular/core';
import { NgForm } from '@angular/forms';
import { CreateJobsService } from './create-jobs.service';
import { CookieService } from 'ngx-cookie-service';
import { Subscription } from 'rxjs';
import { Job } from 'src/app/entity/job.model';
import { Category } from 'src/app/entity/category.model';

@Component({
  selector: 'app-create-jobs',
  templateUrl: './create-jobs.component.html',
  styleUrls: ['./create-jobs.component.css']
})
export class CreateJobsComponent implements OnInit, OnDestroy {

  @ViewChild('f') signupForm: NgForm;

  private sub: Subscription;
  private sub2: Subscription;

  private categories: Category[] = [];
  private catWithParent: Category[] = [];
  private selectedCat: Category;
  private selected = false;

  constructor(
    private jobService: CreateJobsService,
    private cookieService: CookieService
  ) {}

  ngOnInit() {
    this.sub = new Subscription();
    this.sub2 = new Subscription();
    this.sub2 = this.jobService.getAllCategories().subscribe(
      response => {
        this.categories = [];
        console.log(response);
        const obj = JSON.stringify(response);
        const obj2 = JSON.parse(obj);
        const obj3: any[] = Array.of(JSON.parse(obj2[Object.keys(obj2)[0]]));
        const obj4 = obj3[0];
        console.log(obj4._embedded);
        const obj5: any[] = obj4._embedded.jobCategories;
        const length = obj5.length;
        console.log(length);
        for (let i = 0; i < length; i++) {
          const category = new Category(
            obj5[Object.keys(obj5)[i]].jobName,
            obj5[Object.keys(obj5)[i]].about,
            obj5[Object.keys(obj5)[i]].resourceId,
            obj5[Object.keys(obj5)[i]].parentId
          );
          this.categories.push(category);
          if (category.parentid != null) {
            this.catWithParent.push(category);
          }
          console.log(obj5[Object.keys(obj5)[i]]);
        }
      },
      error => console.log(error)
    );
  }

  onSelect(index: number) {
    this.selected = true;
    this.selectedCat = this.catWithParent[index];
  }

  onUpload() {
    const job = new Job(
      this.cookieService.get('USERNAME'),
      this.signupForm.value.userData.name,
      this.signupForm.value.userData.desc,
      [+this.selectedCat.id, +this.selectedCat.parentid]
    );
    this.sub = this.jobService.sendUpload(job).subscribe(
      response => { console.log(response); },
      error => { console.log(error); }
    );
  }

  ngOnDestroy() {
    this.sub.unsubscribe();
    this.sub2.unsubscribe();
  }

}
