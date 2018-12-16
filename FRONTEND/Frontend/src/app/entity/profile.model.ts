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
    username: string, email: string,
    firstname: string, lastname: string,
    about: string, schools: School[],
    workplaces: Workplace[]
  ) {
    this.username = username;
    this.email = email;
    this.firstName = firstname;
    this.lastName = lastname;
    this.aboutMe = about;
    this.schools = schools;
    this.workPlaces = workplaces;
  }
}
