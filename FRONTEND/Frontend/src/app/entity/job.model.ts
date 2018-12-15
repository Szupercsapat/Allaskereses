export class Job {
  public username: string;
  public name: string;
  public description: string;
  public categories: number[];
  // public categories: number[];

  constructor(username: string, name: string, description: string, categories: number[]) {
    this.username = username;
    this.name = name;
    this.description = description;
    this.categories = categories;
  }
}
