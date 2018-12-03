import { School } from './schools.model';
import { Workplace } from './workplaces.model';

export class Profile {
  public id: string;
  public username: string;
  public email: string;
  public firstName: string;
  public lastName: string;
  public aboutMe: string;
  public schools: School[];
  public workPlaces: Workplace[];

  constructor(
    name: string, mail: string,
    first: string, last: string,
    about: string, schools: School[],
    workplaces: Workplace[]
  ) {
    this.username = name;
    this.email = mail;
    this.firstName = first;
    this.lastName = last;
    this.aboutMe = about;
    this.schools = schools;
    this.workPlaces = workplaces;
  }
}
