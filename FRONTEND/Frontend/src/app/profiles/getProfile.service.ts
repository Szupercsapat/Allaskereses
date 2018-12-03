import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable, Subscription } from 'rxjs';
// import { CookieService } from 'ngx-cookie-service';

@Injectable()
export class GetProfileService {

 // private sub = new Subscription();

  constructor(
    private http: Http,
    // private cookieService: CookieService
  ) { console.log('profil const'); }

  public firstName: string;
  public lastName: string;
  public aboutMe: string;
  public username: string;
  public email: string;
  public activated: string;
  public id: string;

  /* asd(): string {
    console.log('asdasdasd');
    return this.firstName;
  }*/

  public getJobSeekerData(data: string) {
    const obj4 = data.search('firstName');
    const first = 'firstName'.length;
    const obj5 = data.search('lastName');
    const last = 'lastName'.length;
    this.firstName = data.slice(obj4 + first + 7, obj5 - 11);
    const obj6 = data.search('aboutMe');
    const about = 'aboutMe'.length;
    this.lastName = data.slice(obj5 + last + 7, obj6 - 11);
    const obj7 = data.search('_links');
    this.aboutMe = data.slice(obj6 + about + 7, obj7 - 11);
  }

  private getSchoolData(data: string) {
    /*const obj4 = data.search('firstName');
    const first = 'firstName'.length;
    const obj5 = data.search('lastName');
    const last = 'lastName'.length;
    this.firstName = data.slice(obj4 + first + 7, obj5 - 11);
    const obj6 = data.search('aboutMe');
    const about = 'aboutMe'.length;
    this.lastName = data.slice(obj5 + last + 7, obj6 - 11);
    const obj7 = data.search('_links');
    this.aboutMe = data.slice(obj6 + about + 7, obj7 - 11);*/
  }

  private getWorkData(data: string) {
    /*const obj4 = data.search('firstName');
    const first = 'firstName'.length;
    const obj5 = data.search('lastName');
    const last = 'lastName'.length;
    this.firstName = data.slice(obj4 + first + 7, obj5 - 11);
    const obj6 = data.search('aboutMe');
    const about = 'aboutMe'.length;
    this.lastName = data.slice(obj5 + last + 7, obj6 - 11);
    const obj7 = data.search('_links');
    this.aboutMe = data.slice(obj6 + about + 7, obj7 - 11);*/
  }

  public getUserData(data: string) {
    const obj4 = data.search('username');
    const user = 'username'.length;
    const obj5 = data.search('email');
    const email = 'email'.length;
    this.username = data.slice(obj4 + user + 7, obj5 - 11);
    const obj6 = data.search('activated');
    const activated = 'activated'.length;
    this.email = data.slice(obj5 + email + 7, obj6 - 11);
    const obj7 = data.search('resourceId');
    const id = 'resourceId'.length;
    this.activated = data.slice(obj6 + activated + 5, obj7 - 9);
    //console.log('Activated: ' + this.activated);
    const obj8 = data.search('_links');
    this.id = data.slice(obj7 + id + 5, obj8 - 9);
    //console.log('ID: ' + this.id);
  }

  private getCategoriesData(data: string) {
    /*const obj4 = data.search('firstName');
    const first = 'firstName'.length;
    const obj5 = data.search('lastName');
    const last = 'lastName'.length;
    this.firstName = data.slice(obj4 + first + 7, obj5 - 11);
    const obj6 = data.search('aboutMe');
    const about = 'aboutMe'.length;
    this.lastName = data.slice(obj5 + last + 7, obj6 - 11);
    const obj7 = data.search('_links');
    this.aboutMe = data.slice(obj6 + about + 7, obj7 - 11);*/
  }

  public getProfileSeeker(id: number): Observable<Response> {
    const seekerUrl = 'http://localhost:8080/rft/users/' + id + '/jobSeeker';
    /*this.sub.add(this.http.get(seekerUrl).subscribe(
      response => {
        const data = JSON.stringify(response);
        const obj = JSON.parse(data);
        const obj2 = obj[Object.keys(obj)[0]];
        console.log(obj2);
        this.getJobSeekerData(JSON.stringify(obj2));

      },
      err => { console.log(err); },
      () => { this.sub.unsubscribe(); }
    ));*/
    return this.http.get(seekerUrl);

    /*const schoolUrl = 'http://localhost:8080/rft/jobSeekers/' + this.cookieService.get('ID') + '/schools';
    //return this.http.get(seekerUrl);
    this.http.get(schoolUrl).subscribe(
      response => {
        const data = JSON.stringify(response);
        const obj = JSON.parse(data);
        const obj2 = obj[Object.keys(obj)[0]];
        console.log(obj2);
        this.getSchoolData(JSON.stringify(obj2));
      }
    );

    const workUrl = 'http://localhost:8080/rft/jobSeekers/' + this.cookieService.get('ID') + '/workPlaces';
    //return this.http.get(seekerUrl);
    this.http.get(workUrl).subscribe(
      response => {
        const data = JSON.stringify(response);
        const obj = JSON.parse(data);
        const obj2 = obj[Object.keys(obj)[0]];
        console.log(obj2);
        this.getWorkData(JSON.stringify(obj2));
      }
    );*/
    }
    public getProfileUser(id: number) {
    const userUrl = 'http://localhost:8080/rft/jobSeekers/' + id + '/user';
    return this.http.get(userUrl);
    /*this.http.get(userUrl).subscribe(
      response => {
        const data = JSON.stringify(response);
        const obj = JSON.parse(data);
        const obj2 = obj[Object.keys(obj)[0]];
        console.log(obj2);
        this.getUserData(JSON.stringify(obj2));
      }
    );*/

    /*const catUrl = 'http://localhost:8080/rft/jobSeekers/' + this.cookieService.get('ID') + '/categories';
    //return this.http.get(seekerUrl);
    this.http.get(catUrl).subscribe(
      response => {
        const data = JSON.stringify(response);
        const obj = JSON.parse(data);
        const obj2 = obj[Object.keys(obj)[0]];
        console.log(obj2);
        this.getCategoriesData(JSON.stringify(obj2));
      }
    );*/
  }
}
