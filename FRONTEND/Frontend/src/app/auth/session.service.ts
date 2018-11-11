import { Injectable } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { Http, Headers, Response } from '@angular/http';
import { Observable } from 'rxjs/internal/Observable';

@Injectable()
export class SessionService {

  private userUrl = 'http://localhost:8080/rft/oauth/token';

  constructor(
    private cookieService: CookieService,
    private http: Http
  ) {}

  refreshSession(): Observable<Response> {
    const header = new Headers();
    header.append('Authorization', 'Basic ' + /*btoa('myclientapp:9999')*/'bXljbGllbnRhcHA6OTk5OQ==');
    header.append('Content-Type', 'application/x-www-form-urlencoded');

    const body =
    'grant_type=refresh_token&refresh_token=' + this.cookieService.get('refresh_token');

    console.log('anyád refresh');

    return this.http.post(this.userUrl, body, {headers: header});
  }

  isAuthenticated(): Observable<Response> {

    const header = new Headers();
    header.append('Authorization', 'Bearer ' + this.cookieService.get('access_token'));

    return this.http.get('http://localhost:8080/rft/profile/test', {headers: header});

    /*if (this.cookieService.get('access_token') === '') {
      console.log('isAuthenticated: nincs token');
      return 'Missing_Token';
    }*/
    /*if (this.cookieService.get('access_token') !== '') {

      const header = new Headers();
      header.append('Authorization', 'Bearer ' + this.cookieService.get('access_token'));

      this.http.get('http://localhost:8080/rft/profile/test', {headers: header}).subscribe(
        response  => {
          const data = JSON.stringify(response);
          const obj = JSON.parse(data);
          const obj2 = obj[Object.keys(obj)[0]];
          console.log('obj2: ' + obj2);
            console.log('isAuthenticated: minden ok');
            return 'OK';
        },
      err => {
        console.log(err);
        const data = JSON.stringify(err);
          const obj = JSON.parse(data);
          const obj2 = obj[Object.keys(obj)[0]];
          console.log('obj2: ' + obj2);
          const obj3 = JSON.parse(obj2);
          console.log('obj3: ' + obj3);
          const answer2 = obj3[Object.keys(obj3)[0]];
          console.log('answer2: ' + answer2);
          if (answer2 === 'invalid_token') {
            console.log('isAuthenticated: session expoired');
            return 'Expired_Token';
          }
      }
      );
    }*/
  }
}
