import { Component, ViewChild, OnDestroy } from '@angular/core';
import { NgForm } from '@angular/forms';
import { RegistrationService } from './registration.service';
import { User } from '../../entity/user.model';
import { Subscription } from 'rxjs/internal/Subscription';
import { Router } from '@angular/router';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnDestroy {

  @ViewChild('f') signupForm: NgForm;

  private user: User;
  private subscription: Subscription = new Subscription();

  constructor(
    private regiService: RegistrationService,
    private router: Router
  ) {}

  onRegister() {
    this.user = new User(
      this.signupForm.value.userData.username,
      this.signupForm.value.userData.email,
      this.signupForm.value.userData.password,
      'ROLE_USER'
    );
    console.log(this.user.username);
    console.log(this.user.email);
    console.log(this.user.password);
    this.subscription = this.regiService.onSubmit(this.user).subscribe(
      response => {
        console.log(response);
        this.router.navigate(['/login']);
      },
      error => console.log(error)
    );
    delete this.user;
    this.signupForm.reset();
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }
}
