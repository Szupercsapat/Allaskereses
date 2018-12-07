import { Injectable } from '@angular/core';
import { Http, Headers } from '@angular/http';

@Injectable()
export class RegistrationService {

  private userUrl = 'http://localhost:8080/rft/user/register';

  // data;

  constructor(private http: Http) {}

  public onSubmit(body: {}) {
    const header = new Headers();
    header.append('Content-Type', 'application/json');

    return this.http.post(this.userUrl, body, {headers: header})/*.subscribe(
      data  => { this.data = JSON.stringify(data);
        const obj = JSON.parse(this.data);
        const obj2 = obj[Object.keys(obj)[0]];
      },
      err => console.log(err)
    )*/;
  }
}
