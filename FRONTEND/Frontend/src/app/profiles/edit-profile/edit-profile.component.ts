import { Component, OnDestroy, ViewChild, OnInit } from '@angular/core';
import { ImageSnippet } from 'src/app/entity/image.model';
import { Subscription } from 'rxjs/internal/Subscription';
import { ProfileUpdateService } from '../update.service';
import { GetProfileService } from '../getProfile.service';
import { CookieService } from 'ngx-cookie-service';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { ImageService } from '../image.service';
import { Profile } from 'src/app/entity/profile.model';
import { NgForm } from '@angular/forms';
import { School } from 'src/app/entity/schools.model';
import { Workplace } from 'src/app/entity/workplaces.model';

@Component({
  selector: 'app-edit-profile',
  templateUrl: './edit-profile.component.html',
  styleUrls: ['./edit-profile.component.css']
})
export class EditProfileComponent implements OnInit, OnDestroy {

  @ViewChild('vezetek') vezetek: NgForm;
  @ViewChild('kereszt') kereszt: NgForm;
  @ViewChild('jelszo') jelszo: NgForm;
  @ViewChild('about') about: NgForm;
  @ViewChild('school') school: NgForm;
  @ViewChild('work') work: NgForm;

  // private categories: number[] = [1, 2, 3, 4];

  private actualProfile:  Profile = new Profile ('Nevem', 'Email', 'Kereszt', 'Vezeték', 'fjfghghjtfrjfrtjfrtjgfjfgjtrjtrjgfjfgjfjtrj',
    [new School(1990, 2000, 'Iskola'), new School(15646, 6456, 'Iskola2')],
    [new Workplace(1990, 2000, 'Munkahely')]
  );

  private modify = false;

  private user: {
    id: number,
    username: string
  };

  private imageUrl: string;

  private subscription: Subscription;
  private paramsSubscription: Subscription;
  private profileSubscription: Subscription;
  private imageSubscription: Subscription;
  private passwordSubscription: Subscription;

  selectedFile: ImageSnippet;

  constructor(
    private updateService: ProfileUpdateService,
    private getProfileService: GetProfileService,
    private cookieService: CookieService,
    private route: ActivatedRoute,
    private imageService: ImageService,
    private router: Router
  ) {}

  ngOnInit() {
    this.subscription = new Subscription();
    this.paramsSubscription = new Subscription();
    this.profileSubscription = new Subscription();
    this.imageSubscription = new Subscription();
    this.passwordSubscription = new Subscription();
    this.user = {
      id: this.route.snapshot.params['id'],
      username: ''
    };
    const num: number = parseInt(this.cookieService.get('ID'), 10);

    if (+this.user.id === +num ) {
    } else {
      this.router.navigate(['/profile/' + this.user.id]);
    }

    this.paramsSubscription = this.route.params
      .subscribe(
        (params: Params) => {
          this.user.id = params['id'];
        }
      );

    this.profileSubscription = this.getProfileService.getProfileSeeker(this.user.id).subscribe(
      response => {
        const data = JSON.stringify(response);
        const obj = JSON.parse(data);
        const obj2 = obj[Object.keys(obj)[0]];
        const obj3 = JSON.parse(obj2);
        this.getProfileService.getJobSeekerData(obj3);
      },
      err => { console.log(err); },
      () => {
        this.actualProfile = new Profile(
          this.getProfileService.username,
          '',
          this.getProfileService.firstName, this.getProfileService.lastName,
          this.getProfileService.aboutMe,
          [], []

        );
        this.kereszt.value.userData.firstname = this.getProfileService.firstName;
        this.vezetek.value.userData.lastname = this.getProfileService.lastName;
        this.about.value.userData.aboutMe = this.getProfileService.aboutMe;
        this.user.username = this.getProfileService.username;
        this.imageUrl = 'http://localhost:8080/rft/seeker/getProfileImage/username/' + this.user.username;
      }
      );

      this.getProfileService.getProfileUser(this.user.id).subscribe(
        response => {
          const data = JSON.stringify(response);
          const obj = JSON.parse(data);
          const obj2 = obj[Object.keys(obj)[0]];
          const obj3 = JSON.parse(obj2);
          this.getProfileService.getEmail(obj3);
        },
        err => { console.log(err); },
        () => {
          this.actualProfile.email = this.getProfileService.email;
        }
        );

      this.getProfileService.getProfileSchools(this.user.id).subscribe(
        response => {
          const data = JSON.stringify(response);
          const obj = JSON.parse(data);
          const obj2 = obj[Object.keys(obj)[0]];
          const obj3 = JSON.parse(obj2);
          this.getProfileService.getSchools(obj3);
        },
        error => console.log(error),
        () => {
          this.actualProfile.schools = this.getProfileService.schools;
        }
      );

      this.getProfileService.getProfileWork(this.user.id).subscribe(
        response => {
          const data = JSON.stringify(response);
          const obj = JSON.parse(data);
          const obj2 = obj[Object.keys(obj)[0]];
          const obj3 = JSON.parse(obj2);
          this.getProfileService.getWork(obj3);
        },
        error => console.log(error),
        () => {
          this.actualProfile.workPlaces = this.getProfileService.works;
        }
      );
  }

  onBack() {
    this.modify = !this.modify;
    this.router.navigate(['profile/' + this.user.id]);
  }

  onChangePassword() {
    const body = {
      'username': this.cookieService.get('USERNAME'),
      'password': this.jelszo.value.userData.pass,
      'newPassword': this.jelszo.value.userData.newpass
    };
    this.passwordSubscription = this.updateService.sendChangePassword(body).subscribe(
      response => console.log(response),
      error => console.log(error)
    );
  }

  onUpdate() {

   // this.onChangePassword();
    const profile = new Profile(
      this.cookieService.get('USERNAME'), '',
      this.kereszt.value.userData.firstname, this.vezetek.value.userData.lastname,
      this.about.value.userData.aboutMe,
      this.actualProfile.schools, this.actualProfile.workPlaces
    );
    this.subscription = this.updateService.sendUpdate(profile).subscribe(
      response => console.log(response),
      error => console.log(error)
    );
    this.modify = false;
    // window.location.reload();
  }

  onAddSchool() {
      this.actualProfile.schools.push(new School(
        this.school.value.userData.schoolFromYear,
        this.school.value.userData.schoolToYear,
        this.school.value.userData.schoolName
      ));
      console.log(this.school.value.userData.schoolFromYear);
      console.log(this.school.value.userData.schoolName);
  }

  onDeleteSchool(index: number) {
    this.actualProfile.schools.splice(index, 1);
  }

  onAddWork() {
    this.actualProfile.workPlaces.push(new Workplace(
      this.work.value.userData.workFromYear,
      this.work.value.userData.workToYear,
      this.work.value.userData.workName
    ));
  }

  onDeleteWork(index: number) {
    this.actualProfile.workPlaces.splice(index, 1);
  }

  isActual() {
    const num: number = parseInt(this.cookieService.get('ID'), 10);
    if (+this.user.id === +num ) {
      return true;
    } else {
      return false;
    }
  }

  processFile(imageInput: any) {
    const file: File = imageInput.files[0];
    const reader = new FileReader();

    reader.addEventListener('load', (event: any) => {

    this.selectedFile = new ImageSnippet(event.target.result, file);

     this.imageSubscription = this.imageService.uploadImageSeeker(this.selectedFile.file, this.user.username).subscribe(
        (res) => {
          console.log(res);
          // window.location.reload();
        },
        (err) => {
          console.log(err);
        });
    });

    reader.readAsDataURL(file);
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
    this.paramsSubscription.unsubscribe();
    this.profileSubscription.unsubscribe();
    this.imageSubscription.unsubscribe();
    this.paramsSubscription.unsubscribe();
  }
}
