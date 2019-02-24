import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs';

@Injectable()
export class GetOffererService {

  constructor(private http: Http) {}

  public firstName: string;
  public lastName: string;
  public aboutMe: string;
  public username: string;
  public email: string;
  public activated: string;
  public id: string;
  public categories: number[] = [];

  public getJobOffererData(data: {}) {
    console.log(data);
    this.id = data[Object.keys(data)[0]];

    this.username = data[Object.keys(data)[1]];

    this.firstName = data[Object.keys(data)[2]];

    this.lastName = data[Object.keys(data)[3]];

    this.aboutMe = data[Object.keys(data)[4]];

    this.categories = data[Object.keys(data)[5]];

  }


  public getEmail(data: {}) {
    this.email = data[Object.keys(data)[3]];
  }

  getAllCategories() {
    const url = 'http://localhost:8080/rft/jobCategories';

    return this.http.get(url);
  }

  getCategoryNames(data: number[]) {
    const length = data.length;
    let url = 'http://localhost:8080/rft/jobCategories/search/findByIdIn?ids=' + data[0];
    for (let i = 1; i < length; i++) {
      url = url + ',' + data[i];
    }
    console.log(url);
    return this.http.get(url);
  }

  public getProfileOfferer(id: number): Observable<Response> {
    const seekerUrl = 'http://localhost:8080/rft/offerer/offerer/' + id;
    return this.http.get(seekerUrl);
  }

  public getProfileUser(id: number): Observable<Response> {
    const userUrl = 'http://localhost:8080/rft/user/user/' + id;
    return this.http.get(userUrl);
  }

  public getProfileWork(id: number): Observable<Response> {
    const workUrl = 'http://localhost:8080/rft/seeker/workPlaces/' + id;
    return this.http.get(workUrl);
  }
}
