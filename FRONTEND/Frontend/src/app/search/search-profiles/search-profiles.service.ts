import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs';
import { Profile } from 'src/app/entity/profile.model';

@Injectable()
export class GetPagedProfilesService {

  public profiles: Profile[] = [];
  public IDs: number[] = [];
  public imageURLs: string[] = [];

  constructor(private http: Http) {}

  getData(data: Profile[]) {
    const length = data.length;
    console.log(data);
    this.profiles = [];
    this.IDs = [];
    this.imageURLs = [];

    for (let i = 0; i < length; i++) {
      const prof = new Profile(
        data[i].username, '', data[i].firstName, data[i].lastName,
        data[i].aboutMe, [], []
      );
      this.IDs.push(data[i][Object.keys(data[i])[0]]);
      this.profiles.push(prof);
      this.imageURLs.push('http://localhost:8080/rft/seeker/getProfileImage/username/' + data[i].username);
    }
  }

  getPagedProfiles(page: number) {
    const url = 'http://localhost:8080/rft/seeker/seekers/page/' + page + '/size/5';
    return this.http.get(url);
  }

  getProfilesCount() {
    const url = 'http://localhost:8080/rft/user/getAllCount';
    return this.http.get(url);
  }
}
