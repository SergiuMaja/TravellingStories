import { User } from "../users/user.model";
import { Injectable } from "@angular/core";
import { Http } from "@angular/http";
import { Globals } from "../shared/globals.service";

@Injectable()
export class AuthService {
  authenticatedUser: User;

  constructor(private http: Http,
              private globals: Globals) {}

  signupUser (user: User) {
    return this.http.post('http://'+ this.globals.host +'/user/save', user);
  }

  signinUser (screenName: string, password: string) {
    const body = {
      "screenName": screenName,
      "password": password
    };

    return this.http.post('http://'+ this.globals.host +'/login', body);
  }

  isAuthenticated() {
    return this.authenticatedUser != null;
  }

  isAdminAuthenticated() {
    return this.isAuthenticated() && this.authenticatedUser.screenName === 'admin';
  }

  isRegularUserAuthenticated() {
    return this.isAuthenticated() && this.authenticatedUser.screenName !== 'admin';
  }
}
