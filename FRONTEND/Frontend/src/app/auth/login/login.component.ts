import { Component, ViewChild, OnDestroy } from '@angular/core';
import { LoginService } from './login.service';
import { NgForm } from '@angular/forms';
import { User } from '../../entity/user.model';
import { CookieService } from 'ngx-cookie-service';
import { SessionService } from '../session.service';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs/internal/Subscription';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['../../app.component.css']
})
export class LoginComponent implements OnDestroy {

  @ViewChild('f') signupForm: NgForm;

  private user: User;
  private subscription: Subscription = new Subscription();

  constructor(
    private loginService: LoginService,
    private cookieService: CookieService,
    private session: SessionService,
    private router: Router
  ) {}

  onLogin() {

    this.user = new User(
      this.signupForm.value.userData.username,
      '',
      this.signupForm.value.userData.password
    );
    this.subscription.add(
      this.loginService.onSendLogin(this.user).subscribe(
        response  => {
          const data = JSON.stringify(response);
          const obj = JSON.parse(data);
          const obj2 = obj[Object.keys(obj)[0]];
          console.log('obj2: ' + obj2);
          const obj3 = JSON.parse(obj2);
          console.log('obj3: ' + obj3);
          const access_token = obj3[Object.keys(obj3)[0]];
          console.log('token: ' + access_token);
          const refresh_token = obj3[Object.keys(obj3)[2]];
          console.log('refresh_token: ' + refresh_token);
          const expire = obj3[Object.keys(obj3)[3]];
          console.log('expire: ' + expire);
          this.cookieService.set('access_token', access_token, expire);
          this.cookieService.set('refresh_token', refresh_token);
          this.router.navigate(['home']);
        },
        err => console.log(err)
      )
    );
    delete this.user;
  }

  onTest() {

    console.log('isauth: ');
    console.log(this.session.isAuthenticated());

  }

  ngOnDestroy() {
    console.log('login unsub');
    this.subscription.unsubscribe();
  }
}
