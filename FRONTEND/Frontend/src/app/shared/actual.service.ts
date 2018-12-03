import { Injectable } from '@angular/core';

@Injectable()
export class ActualService {

  private username: string;
  private id: string;

  public setActual(username: string, id: string) {
    this.username = username;
    this.id = id;
  }

  public getID(): string {
    return this.id;
  }

  public getUsername(): string {
    return this.username;
  }
}
