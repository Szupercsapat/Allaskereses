import { Injectable } from '@angular/core';
import { Http, Headers, Response, ResponseContentType } from '@angular/http';
import { CookieService } from 'ngx-cookie-service';
import { Observable } from 'rxjs/internal/Observable';
import { HttpHeaders, HttpParams } from '@angular/common/http';

@Injectable()
export class CVService {

  constructor(
    private http: Http,
    private cookieService: CookieService
  ) {}

  public createCV(): Observable<Response> {

    const header = new Headers();
    header.append('Authorization', 'Bearer ' + this.cookieService.get('access_token'));
    header.append('responseType', 'arraybuffer');

    return this.http.get('http://localhost:8080/rft/profile/getCV/asd', {headers: header});

  }

  downloadPDF(): any {
    return this.http.get('http://localhost:8080/rft/profile/getCV/asd', { responseType: ResponseContentType.Blob });
  }
}
