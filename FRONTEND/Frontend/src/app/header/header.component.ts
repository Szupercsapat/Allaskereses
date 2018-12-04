import { Component, OnDestroy, OnInit } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { LogoutService } from '../auth/logout.service';
import { Router } from '@angular/router';
// import { CVService } from '../profiles/cv.service';
// import { Response } from '@angular/http';
import { Subscription } from 'rxjs/internal/Subscription';


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
    // private profilesService: CVService
  ) {}

  ngOnInit() {
    this.subscription = new Subscription();
  }
  /*downloadFile(response: Response) {
    // const fileName = response[0].FileName;
        // const fileImage = response[0].binFileImage;
        // const byteArray = new byteArray()
    const blob = new Blob([
      // response
    ], { type: 'application/pdf' });
    const url = window.URL.createObjectURL(blob);
    window.open(url);
  }*/

  /*public cv() {
    this.profilesService.createCV().subscribe(
      (response) => {
      const file = new Blob([response], { type: 'application/pdf' });
      const fileURL = URL.createObjectURL(file);
      window.open(fileURL);
    },
     err => console.log(err)
    );
  }*/
/*public cv() {
  this.profilesService.downloadPDF().subscribe(
    (res) => {
        console.log('res: ' + res);
        res = JSON.stringify(res);
        console.log('stringres: ' + res);
        res = JSON.parse(res);
     const asd =   new Blob([res.blob()], { type: 'application/pdf' });
    const fileURL = URL.createObjectURL(asd);
    window.open(fileURL);
    },
    err => {
    const asd =   new Blob([err], { type: 'application/pdf' });
    const fileURL = URL.createObjectURL(asd);
    window.open(fileURL);
   }
);
  }*/

  onClick() {
    this.router.navigate(['/profile']);
    // this.router.navigate(['/profile', this.cookieService.get('ID')]); mirának
    // window.location.reload(); mirának kommentelve
  }

  onLogout() {
    this.subscription =
      this.logoutService.destroyToken(this.cookieService.get('access_token')).subscribe(
        response  => {
          console.log(response);
       },
       err => console.log(err)
      );
     this.cookieService.delete('access_token');
     this.cookieService.delete('refresh_token');
     this.cookieService.delete('ID');
     this.cookieService.delete('USERNAME');
     this.router.navigate(['login']);
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }
}
