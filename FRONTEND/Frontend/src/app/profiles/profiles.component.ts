import { Component, OnInit, ViewChild, OnDestroy } from '@angular/core';
import { ProfileUpdateService } from './update.service';
import { NgForm } from '@angular/forms';
import { Subscription } from 'rxjs/internal/Subscription';
import { Profile } from '../entity/profile.model';
import { School } from '../entity/schools.model';
import { Workplace } from '../entity/workplaces.model';
import { GetProfileService } from './getProfile.service';
import { CookieService } from 'ngx-cookie-service';
import { ActivatedRoute, Params } from '@angular/router';
import { ImageSnippet } from '../entity/image.model';
import { ImageService } from './image.service';
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

  // private categories: number[] = [1, 2, 3, 4];

  private schools: School[] = [];
  private workplaces: Workplace[] = [];

  private actualProfile:  Profile = new Profile ('Nevem', 'Email', 'Kereszt', 'Vezeték', 'fjfghghjtfrjfrtjfrtjgfjfgjtrjtrjgfjfgjfjtrj',
    [new School(1990, 2000, 'Iskola'), new School(15646, 6456, 'Iskola2')],
    [new Workplace(1990, 2000, 'Munkahely')]
  );

  /* private actualProfile2:  Profile = new Profile ('Nevem2', 'Email2', 'Kereszt2', 'Vezeték2', 'fjfghghjtfrjfrtjfrtjgfjfgjtrjtrjgfjfgjfjtrj',
    [new School(1990, 2000, 'Iskola'), new School(15646, 6456, 'Iskola2')],
    [new Workplace(1990, 2000, 'Munkahely')]
  );*/

  // private profilok: Profile[] = [this.actualProfile, this.actualProfile2];

  private modify = false;

  private user: {
    id: number,
    username: string
  };

  private imageUrl: string;
  private CVUrl: string;

  private subscription: Subscription;
  private paramsSubscription: Subscription;
  private profileSubscription: Subscription;
  private imageSubscription: Subscription;

  selectedFile: ImageSnippet;

  constructor(
    private updateService: ProfileUpdateService,
    private getProfileService: GetProfileService,
    private cookieService: CookieService,
    private route: ActivatedRoute,
    private imageService: ImageService
    // private router: Router
  ) {
    this.user = {
      id: this.route.snapshot.params['id'],
      username: ''
    };
    this.paramsSubscription = this.route.params
      .subscribe(
        (params: Params) => {
          this.user.id = params['id'];
          console.log('asddasasddsdsaasdda' + this.user.id);
        }
      );
      this.profileSubscription = this.getProfileService.getProfileSeeker(this.user.id).subscribe(
      response => {
        const data = JSON.stringify(response);
        const obj = JSON.parse(data);
        console.log(obj);
        const obj2 = obj[Object.keys(obj)[0]];
        console.log(obj2);
        const obj3 = JSON.parse(obj2);
        console.log(obj3);
        // .getProfileService.getJobSeekerData(JSON.stringify(obj2));
        this.getProfileService.getJobSeekerData(obj3);
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
        /*this.actualProfile.username = this.getProfileService.username;
        // this.actualProfile.email = this.getProfileService.email;
        this.actualProfile.id = this.getProfileService.id;

        this.user.username = this.getProfileService.username;
        this.imageUrl = 'http://localhost:8080/rft/seeker/getProfileImage/username/' + this.user.username;
        this.CVUrl = 'http://localhost:8080/rft/seeker/getCV/username/' + this.user.username;*/
      }
      );

      this.getProfileService.getProfileUser(this.user.id).subscribe(
        response => {
          const data = JSON.stringify(response);
          const obj = JSON.parse(data);
          const obj2 = obj[Object.keys(obj)[0]];
         // console.log(obj2);
          this.getProfileService.getUserData(JSON.stringify(obj2));
        },
        err => { console.log(err); },
        () => {
          this.actualProfile.username = this.getProfileService.username;
          this.actualProfile.email = this.getProfileService.email;
          this.actualProfile.id = this.getProfileService.id;

          this.user.username = this.getProfileService.username;
          this.imageUrl = 'http://localhost:8080/rft/seeker/getProfileImage/username/' + this.user.username;
          this.CVUrl = 'http://localhost:8080/rft/seeker/getCV/username/' + this.user.username;
        }
        );
  }

  ngOnInit() {
    console.log('oninit sub');
    this.subscription = new Subscription();
    this.paramsSubscription = new Subscription();
    this.profileSubscription = new Subscription();
    this.imageSubscription = new Subscription();
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
    const profile = new Profile(
      this.cookieService.get('USERNAME'), this.signupForm.value.userData.email,
      this.signupForm.value.userData.firstname, this.signupForm.value.userData.lastname,
      this.signupForm.value.userData.about,
      this.schools, this.workplaces
    );
    this.updateService.sendUpdate(profile);
    this.modify = false;
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

     this.imageSubscription = this.imageService.uploadImage(this.selectedFile.file, this.user.username).subscribe(
        (res) => {
          console.log(res);
          window.location.reload();
        },
        (err) => {
          console.log(err);
        });
    });

    reader.readAsDataURL(file);
  }

  downloadCV() {
    window.open(this.CVUrl);
  }

  ngOnDestroy() {
    console.log('ondest sub');
    this.subscription.unsubscribe();
    this.paramsSubscription.unsubscribe();
    this.profileSubscription.unsubscribe();
    this.imageSubscription.unsubscribe();
  }

}
