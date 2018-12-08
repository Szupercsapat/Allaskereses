import { Component, OnInit, OnDestroy } from '@angular/core';
import { Http } from '@angular/http';
import { Subscription } from 'rxjs';
import { Profile } from 'src/app/entity/profile.model';

@Component({
  selector: 'app-search-profiles',
  templateUrl: './search-profiles.component.html',
  styleUrls: ['./search-profiles.component.css']
})
export class SearchProfilesComponent implements OnInit, OnDestroy {

  private sub: Subscription;

  private page = 0;

  private profiles: Profile[] = [];

  constructor(
    private http: Http
  ) { }

  ngOnInit() {
    this.sub = new Subscription();
  }

  asd() {
    const url = 'http://localhost:8080/rft/seeker/seekers/page/' + this.page + '/size/5';
    this.http.get(url).subscribe(
      (response) => {
        console.log(response);
        const obj = JSON.stringify(response);
        const obj2 = JSON.parse(obj);
        const obj3: any[] = Array.of(JSON.parse(obj2[Object.keys(obj2)[0]]));
        const obj4 = obj3[0];
        console.log(obj4[0]);

      },
      error => console.log(error)
    );
  }

  ngOnDestroy() {
    this.sub.unsubscribe();
  }
}
