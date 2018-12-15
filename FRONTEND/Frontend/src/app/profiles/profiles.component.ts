import { Component, OnInit, OnDestroy } from '@angular/core';
import { ProfileUpdateService } from './update.service';
import { Subscription } from 'rxjs/internal/Subscription';
import { Profile } from '../entity/profile.model';
import { School } from '../entity/schools.model';
import { Workplace } from '../entity/workplaces.model';
import { GetProfileService } from './getProfile.service';
import { CookieService } from 'ngx-cookie-service';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { ImageSnippet } from '../entity/image.model';
import { ImageService } from './image.service';

@Component({
  selector: 'app-profiles',
  templateUrl: './profiles.component.html',
  styleUrls: ['./profiles.component.css']
})
export class ProfilesComponent implements OnInit, OnDestroy {

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
  private CVUrl: string;

  private subscription: Subscription;
  private paramsSubscription: Subscription;
  private profileSubscription: Subscription;
  private imageSubscription: Subscription;

  selectedFile: ImageSnippet;

  constructor(
    private getProfileService: GetProfileService,
    private cookieService: CookieService,
    private route: ActivatedRoute,
    private router: Router,
   // private actual: ActualService
  ) {}

  ngOnInit() {
    this.subscription = new Subscription();
    this.paramsSubscription = new Subscription();
    this.profileSubscription = new Subscription();
    this.imageSubscription = new Subscription();
    this.user = {
      id: this.route.snapshot.params['id'],
      username: ''
    };
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
        this.user.username = this.getProfileService.username;
        this.imageUrl = 'http://localhost:8080/rft/seeker/getProfileImage/username/' + this.user.username;
        this.CVUrl = 'http://localhost:8080/rft/seeker/getCV/username/' + this.user.username;
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

  onModify() {
    // this.modify = !this.modify;
    this.router.navigate(['profile/' + this.user.id + '/edit']);
  }

  isActual() {
    const num: number = parseInt(this.cookieService.get('ID'), 10);
    // const num: number = +this.actual.getID();
    if (+this.user.id === +num ) {
      return true;
    } else {
      return false;
    }
  }


  downloadCV() {
    window.open(this.CVUrl);
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
    this.paramsSubscription.unsubscribe();
    this.profileSubscription.unsubscribe();
    this.imageSubscription.unsubscribe();
  }

}
