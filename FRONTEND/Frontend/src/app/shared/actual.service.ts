import { Injectable } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';

@Injectable()
export class ActualService {

  private username: string;
  private id: string;

  constructor(private cookieSerive: CookieService) {}

  public setActual(username: string, id: number) {
    this.username = username;
    this.id = id.toString();
    console.log('ezakurvasdfhdf: ' + this.id);
  }

  public getID(): string {
    return this.id;
  }

  public getUsername(): string {
    return this.username;
  }
}
