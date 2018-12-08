import { Injectable } from '@angular/core';
import { Router, CanActivate } from '@angular/router';
import { SessionService } from './session.service';
import { ActualService } from '../shared/actual.service';
import { CookieService } from 'ngx-cookie-service';

@Injectable()
export class AuthGuardService implements CanActivate {

  constructor(
    private session: SessionService,
    private router: Router,
    private actual: ActualService,
    private cookieService: CookieService
  ) {}

  canActivate(): boolean {
    if (!this.session.isAuthenticated()) {
      this.router.navigate(['login']);
      return false;
    }
    return true;
  }
}
