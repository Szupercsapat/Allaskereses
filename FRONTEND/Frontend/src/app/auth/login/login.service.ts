import { Injectable } from '@angular/core';
import { User } from '../../entity/user.model';
import { Http, Headers, Response } from '@angular/http';
import { Observable } from 'rxjs/internal/Observable';

@Injectable()
export class LoginService {

  private userUrl = 'http://localhost:8080/rft/oauth/token';

  private data;

  constructor(private http: Http) {}

  public onSendLogin(user: User): Observable<Response> {

    const header = new Headers();
    header.append('Authorization', 'Basic ' + /*btoa('myclientapp:9999')*/'bXljbGllbnRhcHA6OTk5OQ==');
    header.append('Content-Type', 'application/x-www-form-urlencoded');

    const body =
    'username=' + user.username + '&password=' + user.password + '&grant_type=password';


    return this.http.post(this.userUrl, body, {headers: header});

  }

  public getID(username: string): Observable<Response> {
    const url = 'http://localhost:8080/rft/user/userIdByUsername/' + username;
    return this.http.get(url);
  }

}
