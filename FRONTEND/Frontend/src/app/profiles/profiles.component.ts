import { Component, OnInit, ViewChild, OnDestroy } from '@angular/core';
import { ProfileUpdateService } from './update.service';
import { NgForm } from '@angular/forms';
import { Subscription } from 'rxjs/internal/Subscription';
import { Profile } from '../entity/profile.model';
import { School } from '../entity/schools.model';
import { Workplace } from '../entity/workplaces.model';

@Component({
  selector: 'app-profiles',
  templateUrl: './profiles.component.html',
  styleUrls: ['./profiles.component.css']
})
export class ProfilesComponent implements OnInit, OnDestroy {

  @ViewChild('f') signupForm: NgForm;

  private subscription: Subscription;

  private schools: School[] = [];
  private workplaces: Workplace[] = [];

  constructor(private updateService: ProfileUpdateService) {}

  ngOnInit() {
    console.log('oninit sub');
    this.subscription = new Subscription();
  }

  onUpdate() {

   /* const body = {
      'username': 'asd',
      'email': 'asd@dfg',
      'firstName': 'apad',
      'lastName': 'fasza',
      'aboutMe': 'ghfghhdh',
      'categories': [4, 3],
      'schools': [
        { 'fromYear': 1995, 'toYear': 2000, 'name': 'ge' },
        { 'fromYear': 2000, 'toYear': 2010, 'name': 'ah' }
      ],
      'workPlaces': [
        { 'fromYear': 1995, 'toYear': 2000, 'name': 'wor' },
        { 'fromYear': 2000, 'toYear': 2010, 'name': 'w' }
      ]
    };*/
    const profile = new Profile(
      this.signupForm.value.userData.username, this.signupForm.value.userData.email,
      this.signupForm.value.userData.firstname, this.signupForm.value.userData.lastname,
      this.signupForm.value.userData.about,
      this.schools, this.workplaces
    );

    /*console.log('username: ' + this.signupForm.value.userData.username);
    console.log('username: ' + this.signupForm.value.userData.email);
    console.log('username: ' + this.signupForm.value.userData.firstname);
    console.log('username: ' + this.signupForm.value.userData.lastname);*/

    console.log('username: ' + this.signupForm.value.userData.about);
    console.log('fromyear:' + this.signupForm.value.userData.schoolFromYear);
    console.log('toyear:' + this.signupForm.value.userData.schoolToYear);
    console.log('name:' + this.signupForm.value.userData.schoolName);

    this.updateService.sendUpdate(profile).subscribe(
      response => console.log(response),
      error => console.log(error)
    );
  }

  addSchool() {
      this.schools.push(new School(
        this.signupForm.value.userData.schoolFromYear,
        this.signupForm.value.userData.schoolToYear,
        this.signupForm.value.userData.schoolName
      ));
  }

  addWork() {
    this.workplaces.push(new Workplace(1000, 1200, 'asd'));
  }

  ngOnDestroy() {
    console.log('ondest sub');
    this.subscription.unsubscribe();
  }

}
