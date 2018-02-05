export class User {
  public id: number;
  public screenName: string;
  public password: string;
  public email: string;
  public registrationDate: string;
  public updatedDate: string;
  public receiveEmail: boolean;

  constructor() {}

  setScreenName(screenName: string) {
    this.screenName = screenName;
  }

  setPassword(password: string) {
    this.password = password;
  }

  setEmail(email: string) {
    this.email = email;
  }
}
