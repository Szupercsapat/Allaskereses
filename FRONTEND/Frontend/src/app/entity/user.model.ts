export class User {
  public username: string;
  public email: string;
  public password: string;

  constructor(name: string, mail: string, pass: string) {
    this.username = name;
    this.email = mail;
    this.password = pass;
  }
}
