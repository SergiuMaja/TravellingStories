import { User } from "../users/user.model";
import { Injectable } from "@angular/core";
import { Http } from "@angular/http";

@Injectable()
export class AuthService {

  constructor(private http: Http) {}

  signupUser (user: User) {
    return this.http.post('http://localhost:8080/user/save', user);
  }
}
