import { Injectable } from '@angular/core';
import { Http, Headers, Response } from '@angular/http';
import { Observable } from 'rxjs';
import { Offerer } from 'src/app/entity/offerer.model';

@Injectable()
export class OffererUpdateService {

  private userUrl = 'http://localhost:8080/rft/offerer/update';

  constructor(private http: Http) {}

  sendChangePassword(body: {}) {
    const header = new Headers();
    header.append('Content-Type', 'application/json');

    return this.http.post('http://localhost:8080/rft/user/changePassword', body, {headers: header});
  }

  sendUpdate(offerer: Offerer): Observable<Response> {
    const header = new Headers();
    // header.append('Authorization', 'Basic ' + /*btoa('myclientapp:9999')*/'bXljbGllbnRhcHA6OTk5OQ==');
    header.append('Content-Type', 'application/json');

    return this.http.put(this.userUrl, offerer, {headers: header});
  }
}
