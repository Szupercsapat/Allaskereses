import { Injectable } from '@angular/core';

@Injectable()
export class ActualService {

  private username: string;
  private id: number;

  public setActual(username: string, id: number) {
    this.username = username;
    this.id = id;
  }

  public getID(): number {
    return this.id;
  }

  public getUsername(): string {
    return this.username;
  }
}
