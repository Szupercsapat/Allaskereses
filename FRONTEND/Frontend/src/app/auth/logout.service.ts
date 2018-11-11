import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/internal/Observable';
import { Http, Response } from '@angular/http';

@Injectable()
export class LogoutService {

  constructor(private http: Http) {}

  destroyToken(access_token: string): Observable<Response> {
    return this.http.delete('http://localhost:8080/rft/tokens/revoke/' + access_token);
  }
}
