import { Component, OnDestroy, ViewChild, OnInit } from '@angular/core';
import { ImageSnippet } from 'src/app/entity/image.model';
import { Subscription } from 'rxjs/internal/Subscription';
import { CookieService } from 'ngx-cookie-service';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { NgForm } from '@angular/forms';
import { ImageService } from '../../image.service';
import { GetOffererService } from '../getOfferer.service';
import { Offerer } from 'src/app/entity/offerer.model';
import { OffererUpdateService } from './update-offerer.service';
import { Category } from 'src/app/entity/category.model';

@Component({
  selector: 'app-edit-offerer',
  templateUrl: './edit-offerer.component.html',
  styleUrls: ['./edit-offerer.component.css']
})
export class EditOffererComponent implements OnInit, OnDestroy {

  @ViewChild('f') signupForm: NgForm;
  @ViewChild('s') signupForm2: NgForm;
  @ViewChild('z') signupForm3: NgForm;
  @ViewChild('w') signupForm4: NgForm;

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
  private passwordSub: Subscription;
  private sub: Subscription;

  private categories: Category[] = [];
  private category: Category;
  private selected = false;

  selectedFile: ImageSnippet;

  constructor(
    private updateService: OffererUpdateService,
    private getOffererService: GetOffererService,
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
    this.passwordSub = new Subscription();
    this.sub = new Subscription();
    this.user = {
      id: this.route.snapshot.params['id'],
      username: ''
    };
    const num: number = parseInt(this.cookieService.get('ID'), 10);

    if (+this.user.id === +num ) {
    } else {
      this.router.navigate(['/offerer/' + this.user.id]);
    }

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
        this.signupForm.value.userData.firstname = this.getOffererService.firstName;
        this.signupForm.value.userData.lastname = this.getOffererService.lastName;
        this.signupForm4.value.userData.aboutMe = this.getOffererService.aboutMe;


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
          // this.actualProfile.workPlaces = this.getProfileService.works;
        }
      );

      this.sub = this.updateService.getAllCategories().subscribe(
        response => {
          this.categories = [];
          console.log(response);
          const obj = JSON.stringify(response);
          const obj2 = JSON.parse(obj);
          const obj3: any[] = Array.of(JSON.parse(obj2[Object.keys(obj2)[0]]));
          const obj4 = obj3[0];
          console.log(obj4._embedded);
          const obj5: any[] = obj4._embedded.jobCategories;
          const length = obj5.length;
          console.log(length);
          for (let i = 0; i < length; i++) {
            const category = new Category(
              obj5[Object.keys(obj5)[i]].jobName,
              obj5[Object.keys(obj5)[i]].about,
              obj5[Object.keys(obj5)[i]].resourceId,
              obj5[Object.keys(obj5)[i]].parentId
            );
            // this.categories.push(category);
            if (category.parentid != null) {
              this.categories.push(category);
            }
            console.log(obj5[Object.keys(obj5)[i]]);
          }
        },
        error => console.log(error)
      );
  }

  onBack() {
    this.modify = !this.modify;
    this.router.navigate(['offerer/' + this.user.id]);
  }

  onChangePassword() {
    const body = {
      'username': this.cookieService.get('USERNAME'),
      'password': this.signupForm.value.userData.pass,
      'newPassword': this.signupForm.value.userData.newpass
    };
    this.passwordSub = this.updateService.sendChangePassword(body).subscribe(
      response => console.log(response),
      error => console.log(error)
    );
  }

  onSelect(index: number) {
    this.selected = true;
    this.category = this.categories[index];
  }

  onDeselect() {
    this.selected = false;
    delete this.category;
    // this.category = null;
  }

  onUpdate() {

    this.onChangePassword();
    const offerer = new Offerer(
      this.cookieService.get('USERNAME'), this.signupForm.value.userData.email,
      this.signupForm.value.userData.firstname, this.signupForm.value.userData.lastname,
      this.signupForm4.value.userData.aboutMe, [+this.category.id, +this.category.parentid]
    );
    console.log('kurva firstname: ' + this.signupForm.value.userData.firstname);
    this.subscription = this.updateService.sendUpdate(offerer).subscribe(
      response => console.log(response),
      error => console.log(error)
    );
    this.modify = false;
    // window.location.reload();
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
    this.passwordSub.unsubscribe();
    this.sub.unsubscribe();
  }
}
