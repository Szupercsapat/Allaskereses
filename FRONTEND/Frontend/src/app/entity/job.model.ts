export class Job {
  public username: string;
  public name: string;
  public description: string;
  // public categories: number[];

  constructor(username: string, name: string, description: string) {
    this.username = username;
    this.name = name;
    this.description = description;
  }
}
