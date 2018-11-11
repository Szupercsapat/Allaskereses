import { Injectable } from '@angular/core';
import { User } from '../../entity/user.model';
import { Http, Headers, Response } from '@angular/http';
import { Observable } from 'rxjs/internal/Observable';

@Injectable()
export class LoginService {

  private userUrl = 'http://localhost:8080/rft/oauth/token';

  private data;
  // private access_token: string;

  constructor(private http: Http) {}

  public onSendLogin(user: User): Observable<Response> {

    const header = new Headers();
    header.append('Authorization', 'Basic ' + /*btoa('myclientapp:9999')*/'bXljbGllbnRhcHA6OTk5OQ==');
    header.append('Content-Type', 'application/x-www-form-urlencoded');

    const body =
    'username=' + user.username + '&password=' + user.password + '&grant_type=password';

    // let access_token = '';

    return this.http.post(this.userUrl, body, {headers: header}); /*.subscribe(
      data  => {
        this.data = JSON.stringify(data);
        const obj = JSON.parse(this.data);
        const obj2 = obj[Object.keys(obj)[0]];
        console.log(obj2);
        const obj3 = JSON.parse(obj2);
        console.log(obj3);
        access_token = obj3[Object.keys(obj3)[0]];
        console.log('token: ' + access_token);
        return access_token;
      },
      err => console.log(err)
    );*/
  }

  /*test(access_token: string) {
    const header = new Headers();
    header.append('Authorization', 'Bearer ' + access_token);

    this.http.get('http://localhost:8080/rft/profile/test', {headers: header}).subscribe(
      response  => {
        const data = JSON.stringify(response);
        const obj = JSON.parse(data);
        const obj2 = obj[Object.keys(obj)[0]];
        console.log('obj2: ' + obj2);
        const obj3 = JSON.parse(obj2);
        console.log('obj3: ' + obj3);
        const asd = obj3[Object.keys(obj3)[0]];
        console.log('token: ' + asd);
        // this.cookieService.set('access_token', access_token, expire);
      },
    err => {
      console.log(err);
      const data = JSON.stringify(err);
        const obj = JSON.parse(data);
        const obj2 = obj[Object.keys(obj)[0]];
        console.log('obj2: ' + obj2);
        const obj3 = JSON.parse(obj2);
        console.log('obj3: ' + obj3);
        const asd = obj3[Object.keys(obj3)[0]];
        console.log('token: ' + asd);
    }
  );
  }*/

}
