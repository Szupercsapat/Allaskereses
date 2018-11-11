import { Component, OnInit, ViewChild } from '@angular/core';
import { LoginService } from './login.service';
import { NgForm } from '@angular/forms';
import { User } from '../../entity/user.model';
import { CookieService } from 'ngx-cookie-service';
import { SessionService } from '../session.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['../../app.component.css']
})
export class LoginComponent implements OnInit {

  @ViewChild('f') signupForm: NgForm;

  private user: User;

  constructor(
    private loginService: LoginService,
    private cookieService: CookieService,
    private session: SessionService
  ) {}

  ngOnInit() {
  }

  onLogin() {
    this.user = new User(
      this.signupForm.value.userData.username,
      '',
      this.signupForm.value.userData.password
    );
    // let access_token = '';
    // let expire: number;
    // let data = '';
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
        this.cookieService.set('refresh_token', refresh_token);
        const expire = obj3[Object.keys(obj3)[3]];
        console.log('expire: ' + expire);
        this.cookieService.set('access_token', access_token, expire);
      },
      err => console.log(err)
    );
    delete this.user;
  }

  onTest() {
    /*console.log(this.cookieService.get('access_token'));
    this.loginService.test(this.cookieService.get('access_token'));*/

    console.log('isauth: ');
    if (this.cookieService.get('access_token') === '') {
      console.log('isAuthenticated: nincs token');
    }
    if (this.cookieService.get('access_token') !== '') {
      this.session.isAuthenticated().subscribe(
        response  => {
          const data = JSON.stringify(response);
          const obj = JSON.parse(data);
          const obj2 = obj[Object.keys(obj)[0]];
          console.log('obj2: ' + obj2);
            console.log('isAuthenticated: minden ok');
        },
        err => {
          console.log(err);
          const data = JSON.stringify(err);
            const obj = JSON.parse(data);
            const obj2 = obj[Object.keys(obj)[0]];
            console.log('obj2: ' + obj2);
            const obj3 = JSON.parse(obj2);
            console.log('obj3: ' + obj3);
            const answer2 = obj3[Object.keys(obj3)[0]];
            console.log('answer2: ' + answer2);
            if (answer2 === 'invalid_token') {
              console.log('isAuthenticated: session expoired');
              this.session.refreshSession().subscribe(
                response  => {
                  const data2 = JSON.stringify(response);
                  const object = JSON.parse(data2);
                  const object2 = object[Object.keys(object)[0]];
                  console.log('object2: ' + object2);
                  const object3 = JSON.parse(object2);
                  console.log('object3: ' + object3);
                  const access_token = object3[Object.keys(object3)[0]];
                  console.log('token: ' + access_token);
                  const refresh_token = object3[Object.keys(object3)[2]];
                  console.log('refresh_token: ' + refresh_token);
                  this.cookieService.set('refresh_token', refresh_token);
                  const expire = object3[Object.keys(object3)[3]];
                  console.log('expire: ' + expire);
                  this.cookieService.set('access_token', access_token, expire);
                },
                  err2 => console.log(err2)
              );
            }
        }
      );
    }
  }
}
