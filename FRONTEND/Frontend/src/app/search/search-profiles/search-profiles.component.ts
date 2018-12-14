import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs';
import { Profile } from 'src/app/entity/profile.model';
import { GetPagedProfilesService } from './search-profiles.service';

@Component({
  selector: 'app-search-profiles',
  templateUrl: './search-profiles.component.html',
  styleUrls: ['./search-profiles.component.css']
})
export class SearchProfilesComponent implements OnInit, OnDestroy {

  private sub: Subscription;
  private sub2: Subscription;

  private page = 0;

  private maxProfiles;

  private maxPages;

  public profiles: Profile[] = [new Profile('', '', '', '', '', [], [])];

  public IDs: number[] = [];

  public imageURL: string[] = [];

  constructor(
    public getPagedService: GetPagedProfilesService,
  ) {}

  ngOnInit() {
    this.sub = new Subscription();
    this.sub = this.getPagedService.getProfilesCount().subscribe(
      response => {
        // console.log(response);
        const obj = JSON.stringify(response);
        const obj2 = JSON.parse(obj);
        // console.log(obj2);
        this.maxProfiles = JSON.parse(obj2[Object.keys(obj2)[0]]);
        this.maxPages = Math.ceil(this.maxProfiles / 5);
      },
      error => {
        console.log(error);
      }
    );
    this.sub2 = new Subscription();
    this.sub2 = this.getPagedService.getPagedProfiles(this.page).subscribe(
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
        this.profiles = this.getPagedService.profiles;
        this.IDs = this.getPagedService.IDs;
        this.imageURL = this.getPagedService.imageURLs;
        // console.log(this.IDs);
      }
    );
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
  }
}
