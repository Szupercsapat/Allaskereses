import { Injectable } from '@angular/core';

@Injectable()
export class ActualService {

  private username: string;
  private id: string;

  public setActual(username: string, id: number) {
    this.username = username;
    this.id = id.toString();
  }

  public getID(): string {
    return this.id;
  }

  public getUsername(): string {
    return this.username;
  }
}
