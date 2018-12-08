import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs';
import { School } from '../entity/schools.model';
import { Workplace } from '../entity/workplaces.model';

@Injectable()
export class GetProfileService {

  constructor(private http: Http) {}

  public firstName: string;
  public lastName: string;
  public aboutMe: string;
  public username: string;
  public email: string;
  public activated: string;
  public id: string;
  public schools: School[] = [];
  public works: Workplace[] = [];

  public getJobSeekerData(data: {}) {
    this.id = data[Object.keys(data)[0]];
    this.username = data[Object.keys(data)[1]];
    this.firstName = data[Object.keys(data)[2]];
    this.lastName = data[Object.keys(data)[3]];
    this.aboutMe = data[Object.keys(data)[4]];
    console.log(data[Object.keys(data)[4]]);
  }

  public getSchools(data: {asd: [], length: number}) {
    this.schools.splice(0, this.schools.length);
    console.log(data);
    const length = data.length;
    for (let i = 0; i < length; i++) {
      const name = data[Object.keys(data)[i]].name;
      console.log(name);
      const toYear = data[Object.keys(data)[i]].toYear;
      console.log(toYear);
      const fromYear = data[Object.keys(data)[i]].fromYear;
      console.log(fromYear);
      this.schools.push(new School(fromYear, toYear, name));
   }
  }

  public getWork(data: {asd: [], length: number}) {
    this.works.splice(0, this.works.length);
    console.log(data);
    const length = data.length;
    for (let i = 0; i < length; i++) {
      const name = data[Object.keys(data)[i]].name;
      console.log(name);
      const toYear = data[Object.keys(data)[i]].toYear;
      console.log(toYear);
      const fromYear = data[Object.keys(data)[i]].fromYear;
      console.log(fromYear);
      this.works.push(new School(fromYear, toYear, name));
   }
  }

  public getEmail(data: {}) {
    this.email = data[Object.keys(data)[3]];
  }

  public getCategoriesData(data: {}) {
  }

  public getProfileSeeker(id: number): Observable<Response> {
    const seekerUrl = 'http://localhost:8080/rft/seeker/seeker/' + id;
    return this.http.get(seekerUrl);
  }

  public getProfileUser(id: number): Observable<Response> {
    const userUrl = 'http://localhost:8080/rft/user/user/' + id;
    return this.http.get(userUrl);
  }

  public getProfileSchools(id: number): Observable<Response> {
    const schoolUrl = 'http://localhost:8080/rft/seeker/schools/' + id;
    return this.http.get(schoolUrl);
  }

  public getProfileWork(id: number): Observable<Response> {
    const workUrl = 'http://localhost:8080/rft/seeker/workPlaces/' + id;
    return this.http.get(workUrl);
  }
}
