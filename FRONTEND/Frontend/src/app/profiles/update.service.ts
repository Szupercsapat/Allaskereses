import { Injectable } from '@angular/core';
import { Http, Headers, Response } from '@angular/http';
import { Observable } from 'rxjs';
import { Profile } from '../entity/profile.model';

@Injectable()
export class ProfileUpdateService {

  private userUrl = 'http://localhost:8080/rft/seeker/update';

  constructor(private http: Http) {}

  sendChangePassword(body: {}) {
    const header = new Headers();
    header.append('Content-Type', 'application/json');

    return this.http.post('http://localhost:8080/rft/user/changePassword', body, {headers: header});
  }

  sendUpdate(profile: Profile): Observable<Response> {
    const header = new Headers();
    // header.append('Authorization', 'Basic ' + /*btoa('myclientapp:9999')*/'bXljbGllbnRhcHA6OTk5OQ==');
    header.append('Content-Type', 'application/json');

    return this.http.put(this.userUrl, profile, {headers: header});
  }
}
