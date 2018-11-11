import { Component, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { RegistrationService } from './registration.service';
import { User } from '../../entity/user.model';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['../../app.component.css']
})
export class RegistrationComponent implements OnInit {

  @ViewChild('f') signupForm: NgForm;

  private user: User;

  constructor(private regiService: RegistrationService) {}

  ngOnInit() {
  }

  onRegister() {
    this.user = new User(
      this.signupForm.value.userData.username,
      this.signupForm.value.userData.email,
      this.signupForm.value.userData.password
    );
    console.log(this.user.username);
    console.log(this.user.email);
    console.log(this.user.password);
    this.regiService.onSubmit(this.user);
    delete this.user;
    this.signupForm.reset();
  }

}
