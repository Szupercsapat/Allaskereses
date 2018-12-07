import { Injectable } from '@angular/core';

@Injectable()
export class LoggedInService {

  private loggedIn = false;

  logIn() {
    this.loggedIn = true;
  }

  logOut() {
    this.loggedIn = false;
  }

  isLoggedIn() {
    return this.loggedIn;
  }
}
