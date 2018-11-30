export class Workplace {
  public fromYear: number;
  public toYear: number;
  public name: string;

  constructor(from: number, to: number, name: string) {
    this.fromYear = from;
    this.toYear = to;
    this.name = name;
  }
}
