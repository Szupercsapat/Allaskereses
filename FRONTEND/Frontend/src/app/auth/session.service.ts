import { Injectable } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { Http, Headers, Response } from '@angular/http';
import { Observable } from 'rxjs/internal/Observable';
import { Subscription } from 'rxjs/internal/Subscription';
// import { GetProfileService } from '../profiles/getProfile.service';

@Injectable()
export class SessionService {

  private userUrl = 'http://localhost:8080/rft/oauth/token';


  public sub: Subscription;
  constructor(
    private cookieService: CookieService,
    private http: Http,
   // private getProfileService: GetProfileService
  ) {}

  refreshSession(): Observable<Response> {
    const header = new Headers();
    header.append('Authorization', 'Basic ' + 'bXljbGllbnRhcHA6OTk5OQ==');
    header.append('Content-Type', 'application/x-www-form-urlencoded');

    const body =
    'grant_type=refresh_token&refresh_token=' + this.cookieService.get('refresh_token');

    return this.http.post(this.userUrl, body, {headers: header});
  }

  isAuthenticated(): boolean {

    const token = this.cookieService.get('access_token');
    if (token === '') {
      console.log('not auth');
      this.cookieService.delete('access_token');
      return false;
    }
    if (token === null) {
      console.log('not auth2');
      this.cookieService.delete('access_token');
      return false;
    }
    console.log('van auth');
    // const subscription = new Subscription();
     /*  this.sub = this.refreshSession().subscribe(
        response  => {
          const data = JSON.stringify(response);
          const object = JSON.parse(data);
          // console.log(object);
          const object2 = object[Object.keys(object)[0]];
          // console.log(object2);
          const object3 = JSON.parse(object2);
          // console.log(object3);
          const access_token = object3[Object.keys(object3)[0]];
          const refresh_token = object3[Object.keys(object3)[2]];
          this.cookieService.delete('access_token');
          this.cookieService.delete('refresh_token');

          this.cookieService.delete('access_token');
          this.cookieService.delete('refresh_token');

          this.cookieService.delete('access_token');
          this.cookieService.delete('refresh_token');
          //this.cookieService.deleteAll();
          this.cookieService.set('refresh_token', refresh_token);
          const expire = object3[Object.keys(object3)[3]];
          this.cookieService.set('access_token', access_token, expire);
          // this.getProfileService.getProfileSeeker();
        },
        err => { console.log(err); },
        () => {  this.sub.unsubscribe(); }
      ); */
      // this.sub.unsubscribe();
    return true;
  }
}
