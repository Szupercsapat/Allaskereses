import { Component, OnInit } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { LogoutService } from '../auth/logout.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  constructor(
    private cookieService: CookieService,
    private logoutService: LogoutService
  ) {}

  ngOnInit() {
  }

  onLogout() {
    this.logoutService.destroyToken(this.cookieService.get('access_token')).subscribe(
      response  => {
        console.log(response);
     },
     err => console.log(err)
   );
   this.cookieService.delete('access_token');
   this.cookieService.delete('refresh_token');
  }
}
