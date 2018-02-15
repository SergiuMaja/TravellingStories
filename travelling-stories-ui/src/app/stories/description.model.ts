export class Description {
  public id: number;
  public type: string;
  public details: string;

  constructor(type: string, details: string) {
    this.type = type;
    this.details = details;
  }
}
