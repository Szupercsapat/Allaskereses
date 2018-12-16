import { Component, OnInit, ViewChild, OnDestroy } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Subscription } from 'rxjs/internal/Subscription';
import { ImageSnippet } from 'src/app/entity/image.model';
import { CookieService } from 'ngx-cookie-service';
import { ActivatedRoute, Router, Params } from '@angular/router';
import { ImageService } from '../image.service';
import { GetOffererService } from './getOfferer.service';
import { Offerer } from 'src/app/entity/offerer.model';

@Component({
  selector: 'app-offerer',
  templateUrl: './offerer.component.html',
  styleUrls: ['./offerer.component.css']
})
export class OffererComponent implements OnInit, OnDestroy {

  @ViewChild('f') signupForm: NgForm;
  @ViewChild('s') signupForm2: NgForm;
  @ViewChild('z') signupForm3: NgForm;

  // private categories: number[] = [1, 2, 3, 4];

  private actualOfferer:  Offerer = new Offerer ('Nevem', 'Email', 'Kereszt', 'Vezeték',
    'fjfghghjtfrjfrtjfrtjgfjfgjtrjtrjgfjfgjfjtrj', []
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

  selectedFile: ImageSnippet;

  constructor(
    private cookieService: CookieService,
    private route: ActivatedRoute,
    private imageService: ImageService,
    private router: Router,
    private getOffererService: GetOffererService
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
      this.profileSubscription = this.getOffererService.getProfileOfferer(this.user.id).subscribe(
      response => {
        const data = JSON.stringify(response);
        const obj = JSON.parse(data);
        const obj2 = obj[Object.keys(obj)[0]];
        const obj3 = JSON.parse(obj2);
        this.getOffererService.getJobOffererData(obj3);
      },
      err => { console.log(err); },
      () => {
        this.actualOfferer = new Offerer(
          this.getOffererService.username,
          '',
          this.getOffererService.firstName, this.getOffererService.lastName,
          this.getOffererService.aboutMe,
          []
        );
        this.user.username = this.getOffererService.username;
        this.imageUrl = 'http://localhost:8080/rft/offerer/getProfileImage/username/' + this.user.username;
      }
      );

      this.getOffererService.getProfileUser(this.user.id).subscribe(
        response => {
          const data = JSON.stringify(response);
          const obj = JSON.parse(data);
          const obj2 = obj[Object.keys(obj)[0]];
          const obj3 = JSON.parse(obj2);
          this.getOffererService.getEmail(obj3);
        },
        err => { console.log(err); },
        () => {
          this.actualOfferer.email = this.getOffererService.email;
        }
        );

      this.getOffererService.getProfileWork(this.user.id).subscribe(
        response => {
          const data = JSON.stringify(response);
          const obj = JSON.parse(data);
          const obj2 = obj[Object.keys(obj)[0]];
          const obj3 = JSON.parse(obj2);
          // this.getOffererService.getWork(obj3);
        },
        error => console.log(error),
        () => {
          // this.actualOfferer.workPlaces = this.getOffererService.works;
          this.actualOfferer.email = this.getOffererService.email;

        }
      );
  }

  onModify() {
    this.router.navigate(['offerer/' + this.user.id + '/edit']);
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

  ngOnDestroy() {
    this.subscription.unsubscribe();
    this.paramsSubscription.unsubscribe();
    this.profileSubscription.unsubscribe();
    this.imageSubscription.unsubscribe();
  }

}
