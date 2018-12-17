import { Component, OnInit } from '@angular/core';
import { Category } from 'src/app/entity/category.model';
import { Subscription } from 'rxjs';
import { HomeService } from './home.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  private sub: Subscription;

  private categoriesf: Category[] = [];
  private categoriesm: Category[] = [];
  private categoriesl: Category[] = [];
  private catWithParent: Category[] = [];
  private selectedCat: Category;
  private selected = false;

  constructor(
    private homeService: HomeService
    ) {}

  ngOnInit() {
    this.sub = new Subscription();
    this.sub = this.homeService.getAllCategories().subscribe(
      response => {
        this.categoriesf = [];
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
          this.categoriesf.push(category);
          if (category.parentid != null) {
            this.catWithParent.push(category);
          }
          console.log(obj5[Object.keys(obj5)[i]]);
          i++;
          i++;
        }
      },
      error => console.log(error)
    );

    this.sub = this.homeService.getAllCategories().subscribe(
      response => {
        this.categoriesl = [];
        console.log(response);
        const obj = JSON.stringify(response);
        const obj2 = JSON.parse(obj);
        const obj3: any[] = Array.of(JSON.parse(obj2[Object.keys(obj2)[0]]));
        const obj4 = obj3[0];
        console.log(obj4._embedded);
        const obj5: any[] = obj4._embedded.jobCategories;
        const length = obj5.length;
        console.log(length);
        for (let i = 2; i < length; i++) {
          const category = new Category(
            obj5[Object.keys(obj5)[i]].jobName,
            obj5[Object.keys(obj5)[i]].about,
            obj5[Object.keys(obj5)[i]].resourceId,
            obj5[Object.keys(obj5)[i]].parentId
          );
          this.categoriesl.push(category);
          if (category.parentid != null) {
            this.catWithParent.push(category);
          }
          console.log(obj5[Object.keys(obj5)[i]]);
          i++;
          i++;
        }
      },
      error => console.log(error)
    );


  this.sub = this.homeService.getAllCategories().subscribe(
    response => {
      this.categoriesm = [];
      console.log(response);
      const obj = JSON.stringify(response);
      const obj2 = JSON.parse(obj);
      const obj3: any[] = Array.of(JSON.parse(obj2[Object.keys(obj2)[0]]));
      const obj4 = obj3[0];
      console.log(obj4._embedded);
      const obj5: any[] = obj4._embedded.jobCategories;
      const length = obj5.length;
      console.log(length);
      for (let i = 1; i < length; i++) {
        const category = new Category(
          obj5[Object.keys(obj5)[i]].jobName,
          obj5[Object.keys(obj5)[i]].about,
          obj5[Object.keys(obj5)[i]].resourceId,
          obj5[Object.keys(obj5)[i]].parentId
        );
        this.categoriesm.push(category);
        if (category.parentid != null) {
          this.catWithParent.push(category);
        }
        console.log(obj5[Object.keys(obj5)[i]]);
        i++;
        i++;
      }
    },
    error => console.log(error)
  );

  }
}
