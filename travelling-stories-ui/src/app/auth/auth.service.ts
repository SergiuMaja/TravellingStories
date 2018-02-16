import { User } from "../users/user.model";
import { Injectable } from "@angular/core";
import { Http } from "@angular/http";

@Injectable()
export class AuthService {
  authenticatedUser: User;

  constructor(private http: Http) {}

  signupUser (user: User) {
    return this.http.post('http://localhost:8080/user/save', user);
  }

  signinUser (screenName: string, password: string) {
    const body = {
      "screenName": screenName,
      "password": password
    };

    return this.http.post('http://localhost:8080/login', body);
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
