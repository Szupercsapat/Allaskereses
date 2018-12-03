export class User {
  public username: string;
  public email: string;
  public password: string;
  public role: string;

  constructor(name: string, mail: string, pass: string, role: string) {
    this.username = name;
    this.email = mail;
    this.password = pass;
    this.role = role;
  }
}
