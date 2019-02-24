import { Injectable } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { Http, Headers, Response } from '@angular/http';
import { Observable } from 'rxjs/internal/Observable';
import { Subscription } from 'rxjs/internal/Subscription';

@Injectable()
export class SessionService {

  private userUrl = 'http://localhost:8080/rft/oauth/token';


  public sub: Subscription;
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
      this.cookieService.delete('access_token');
      return false;
    }
    if (token === null) {
      console.log('not auth2');
      this.cookieService.delete('access_token');
      return false;
    }
    console.log('van auth');

    return true;
  }
}
