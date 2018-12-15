export class Category {
  public name: string;
  public about: string;
  public id: string;
  public parentid: string;

  constructor(name: string, about: string, id: string, parentid: string) {
    this.name = name;
    this.about = about;
    this.id = id;
    this.parentid = parentid;
  }
}
