export class Offerer {
  public id: string;
  public username: string;
  public email: string;
  public firstName: string;
  public lastName: string;
  public aboutMe: string;
  public categories: number[];

  constructor(
    name: string, mail: string,
    first: string, last: string,
    about: string, categories: number[]
  ) {
    this.username = name;
    this.email = mail;
    this.firstName = first;
    this.lastName = last;
    this.aboutMe = about;
    this.categories = categories;
  }
}
