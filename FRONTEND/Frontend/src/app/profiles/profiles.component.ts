import { Component, OnInit, ViewChild, OnDestroy } from '@angular/core';
import { ProfileUpdateService } from './update.service';
import { NgForm } from '@angular/forms';
import { Subscription } from 'rxjs/internal/Subscription';
import { Profile } from '../entity/profile.model';
import { School } from '../entity/schools.model';
import { Workplace } from '../entity/workplaces.model';
import { GetProfileService } from './getProfile.service';
import { CookieService } from 'ngx-cookie-service';
// import { Router } from '@angular/router';

@Component({
  selector: 'app-profiles',
  templateUrl: './profiles.component.html',
  styleUrls: ['./profiles.component.css']
})
export class ProfilesComponent implements OnInit, OnDestroy {

  @ViewChild('f') signupForm: NgForm;
  @ViewChild('s') signupForm2: NgForm;
  @ViewChild('z') signupForm3: NgForm;

  private subscription: Subscription;

  private categories: number[] = [1, 2, 3, 4];

  private schools: School[] = [];
  private workplaces: Workplace[] = [];

  private actualProfile:  Profile = new Profile ('Nevem', 'Email', 'Kereszt', 'Vezeték', 'fjfghghjtfrjfrtjfrtjgfjfgjtrjtrjgfjfgjfjtrj',
    [new School(1990, 2000, 'Iskola'), new School(15646, 6456, 'Iskola2')],
    [new Workplace(1990, 2000, 'Munkahely')]
  );

  private actualProfile2:  Profile = new Profile ('Nevem2', 'Email2', 'Kereszt2', 'Vezeték2', 'fjfghghjtfrjfrtjfrtjgfjfgjtrjtrjgfjfgjfjtrj',
    [new School(1990, 2000, 'Iskola'), new School(15646, 6456, 'Iskola2')],
    [new Workplace(1990, 2000, 'Munkahely')]
  );

  private profilok: Profile[] = [this.actualProfile, this.actualProfile2];

  private modify = false;

  constructor(
    private updateService: ProfileUpdateService,
    private getProfileService: GetProfileService,
    private cookieService: CookieService,
    // private router: Router
  ) {
      this.getProfileService.getProfileSeeker().subscribe(
      response => {
        const data = JSON.stringify(response);
        const obj = JSON.parse(data);
        const obj2 = obj[Object.keys(obj)[0]];
        console.log(obj2);
        this.getProfileService.getJobSeekerData(JSON.stringify(obj2));
      },
      err => { console.log(err); },
      () => {
        this.actualProfile = new Profile(
          //this.getProfileService.username, this.getProfileService.email,
          '', '',
          this.getProfileService.firstName, this.getProfileService.lastName,
          this.getProfileService.aboutMe,
          [new School(1990, 2000, 'Iskola'), new School(15646, 6456, 'Iskola2')],
          [new Workplace(1990, 2000, 'Munkahely')]
        );
      }
      );

      this.getProfileService.getProfileUser().subscribe(
        response => {
          const data = JSON.stringify(response);
          const obj = JSON.parse(data);
          const obj2 = obj[Object.keys(obj)[0]];
          console.log(obj2);
          this.getProfileService.getUserData(JSON.stringify(obj2));
        },
        err => { console.log(err); },
        () => {
          this.actualProfile.username = this.getProfileService.username;
          this.actualProfile.email = this.getProfileService.email;
        }
        );
  }

  ngOnInit() {
    console.log('oninit sub');
    this.subscription = new Subscription();
  }

  onModify() {
    this.modify = !this.modify;
  }

  onChangePassword() {
    const body = {
      'username': this.cookieService.get('USERNAME'),
      'password': this.signupForm.value.userData.pass,
      'newPassword': this.signupForm.value.userData.newpass
    };
    this.updateService.sendChangePassword(body);
  }

  onUpdate() {

    this.onChangePassword();

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
      this.cookieService.get('USERNAME'), this.signupForm.value.userData.email,
      this.signupForm.value.userData.firstname, this.signupForm.value.userData.lastname,
      this.signupForm.value.userData.about,
      this.schools, this.workplaces
    );

    /*console.log('username: ' + this.signupForm.value.userData.about);
    console.log('fromyear:' + this.signupForm.value.userData.schoolFromYear);
    console.log('toyear:' + this.signupForm.value.userData.schoolToYear);
    console.log('name:' + this.signupForm.value.userData.schoolName);*/

    this.updateService.sendUpdate(profile).subscribe(
      response => console.log(response),
      error => console.log(error)
    );

   // this.router.navigateByUrl('profile');
    this.modify = false;
    //this.router.navigateByUrl('profile');

    //this.router.navigate(['profile']);
     window.location.reload();
  }

  onAddSchool() {
      this.actualProfile.schools.push(new School(
        this.signupForm2.value.userData.schoolFromYear,
        this.signupForm2.value.userData.schoolToYear,
        this.signupForm2.value.userData.schoolName
      ));
      console.log(this.signupForm2.value.userData.schoolFromYear);
      console.log(this.signupForm2.value.userData.schoolName);
  }

  onDeleteSchool(index: number) {
    this.actualProfile.schools.splice(index, 1);
  }

  onAddWork() {
    this.actualProfile.workPlaces.push(new Workplace(
      this.signupForm3.value.userData.workFromYear,
      this.signupForm3.value.userData.workToYear,
      this.signupForm3.value.userData.workName
    ));
  }

  onDeleteWork(index: number) {
    this.actualProfile.workPlaces.splice(index, 1);
  }

  ngOnDestroy() {
    console.log('ondest sub');
    this.subscription.unsubscribe();
  }

}
