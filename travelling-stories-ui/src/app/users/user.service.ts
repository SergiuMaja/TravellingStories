import { User } from "./user.model";
import { Injectable } from "@angular/core";
import { Http } from "@angular/http";

@Injectable()
export class UserService {

  private users: User[] = [];

  constructor(private http: Http) {}

  getUsersFromServer() {
    return this.http.get('http://localhost:8080/user/all');
  }

  setUsers(users: User[]) {
    this.users = users;
  }
}
