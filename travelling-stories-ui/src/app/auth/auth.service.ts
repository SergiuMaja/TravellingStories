import { User } from "../users/user.model";
import { Injectable } from "@angular/core";
import { Http } from "@angular/http";
import { Router } from "@angular/router";

@Injectable()
export class AuthService {
  authenticatedUser: User;

  constructor(private http: Http,
              private router: Router) {}

  signupUser (user: User) {
    return this.http.post('http://localhost:8080/user/save', user);
  }

  signinUser (screenName: string, password: string) {
    const body = {
      "screenName": screenName,
      "password": password
    };

    this.http.post('http://localhost:8080/login', body).subscribe(
      (response) => {
        if(response.status === 200) {
          this.authenticatedUser = response.json();
          this.router.navigate(['/']);
        }
      },
      (error) => console.log(error)
    );
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
