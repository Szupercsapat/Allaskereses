import { Injectable, OnDestroy } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { Http, Headers, Response } from '@angular/http';
import { Observable } from 'rxjs/internal/Observable';
import { Subscription } from 'rxjs/internal/Subscription';

@Injectable()
export class SessionService implements OnDestroy {

  private userUrl = 'http://localhost:8080/rft/oauth/token';
  private subscription: Subscription = new Subscription();

  constructor(
    private cookieService: CookieService,
    private http: Http
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
      return false;
    }
    if (token === null) {
      console.log('not auth2');
      return false;
    }
    console.log('van auth');
    this.subscription.add(
      this.refreshSession().subscribe(
        response  => {
          const data2 = JSON.stringify(response);
          const object = JSON.parse(data2);
          const object2 = object[Object.keys(object)[0]];
          console.log('object2: ' + object2);
          const object3 = JSON.parse(object2);
          console.log('object3: ' + object3);
          const access_token = object3[Object.keys(object3)[0]];
          console.log('token: ' + access_token);
          const refresh_token = object3[Object.keys(object3)[2]];
          console.log('refresh_token: ' + refresh_token);
          this.cookieService.set('refresh_token', refresh_token);
          const expire = object3[Object.keys(object3)[3]];
          console.log('expire: ' + expire);
          this.cookieService.set('access_token', access_token, expire);
        },
          err2 => console.log(err2)
      )
    );
    return true;
  }

  ngOnDestroy() {
    console.log('auth unsub');
    this.subscription.unsubscribe();
  }
}
