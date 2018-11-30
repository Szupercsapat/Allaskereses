import { Injectable } from '@angular/core';
import { Http, Headers, Response } from '@angular/http';
import { Observable } from 'rxjs';
import { Profile } from '../entity/profile.model';

@Injectable()
export class ProfileUpdateService {

  private userUrl = 'http://localhost:8080/rft/seeker/update';

  constructor(private http: Http) {}

  sendUpdate(profile: Profile): Observable<Response> {
    const header = new Headers();
    // header.append('Authorization', 'Basic ' + /*btoa('myclientapp:9999')*/'bXljbGllbnRhcHA6OTk5OQ==');
    header.append('Content-Type', 'application/json');

    const body = {
      'username': 'asd',
      'email': 'asd@dfg',
      'firstName': 'apad',
      'lastName': 'fasza',
      'aboutMe': 'ghfghhdh',
      'categories': [4, 3],
      'schools': [
        { 'fromYear': 1995, 'toYear': 2000, 'name': 'ge' },
        { 'fromYear': 2000, 'toYear': 2010, 'name': 'ah' }
      ],
      'workPlaces': [
        { 'fromYear': 1995, 'toYear': 2000, 'name': 'wor' },
        { 'fromYear': 2000, 'toYear': 2010, 'name': 'w' }
      ]
    };

    return this.http.put(this.userUrl, profile, {headers: header});
  }
}