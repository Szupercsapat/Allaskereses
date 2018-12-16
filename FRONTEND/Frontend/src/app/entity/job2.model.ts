export class Job2 {
  public username: string;
  public name: string;
  public description: string;
  public categories: number[];
  public offererId: number;

  constructor(username: string, name: string, description: string, categories: number[], offererId: number) {
    this.username = username;
    this.name = name;
    this.description = description;
    this.categories = categories;
    this.offererId = offererId;
  }
}
