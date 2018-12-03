import { Http, Response, Headers } from '@angular/http';
import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';

@Injectable()
export class ImageService {

  constructor(
    private http: Http,
    private cookieService: CookieService
  ) {}


  public uploadImage(image: File, username: string): Observable<Response> {
    const formData = new FormData();

    formData.append('image', image);
    formData.append('username', username);

    const header = new Headers();
    header.append('Authorization', 'Bearer ' + this.cookieService.get('access_token'));

    return this.http.post('http://localhost:8080/rft/seeker/uploadProfileImage', formData, {headers: header});
  }
}
