import { Component, OnDestroy, OnInit } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { LogoutService } from '../auth/logout.service';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs/internal/Subscription';
import { LoggedInService } from '../shared/loggedin.service';


@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit, OnDestroy {

  private subscription: Subscription;

  constructor(
    private cookieService: CookieService,
    private logoutService: LogoutService,
    private router: Router,
    private loggedInService: LoggedInService
    // private profilesService: CVService
  ) {}

  ngOnInit() {
    // this.subscription = new Subscription();
  }

  onClick() {
     this.router.navigate(['/profile', this.cookieService.get('ID')]);
     window.location.reload();
  }

  onLogout() {

      this.logoutService.destroyToken(this.cookieService.get('access_token'));
      this.cookieService.delete('access_token');
      this.cookieService.delete('refresh_token');
      this.cookieService.delete('ID');
      this.cookieService.delete('USERNAME');
      this.router.navigate(['login']);
  }

  ngOnDestroy() {
    //this.subscription.unsubscribe();
  }
}
