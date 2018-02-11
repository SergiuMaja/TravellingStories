import { User } from "./user.model";
import { Injectable } from "@angular/core";
import { Http, Response } from "@angular/http";
import { Subject } from "rxjs";

@Injectable()
export class UserService {
  usersChanged = new Subject<User[]>();
  private users: User[] = [];

  constructor(private http: Http) {}

  getUsers() {
    return this.http.get('http://localhost:8080/user/all').subscribe(
      (response: Response) => {
        this.setUsers(response.json());
      }
    );
  }

  setUsers(users: User[]) {
    this.users = users;
    this.usersChanged.next(this.users.slice());
  }

  getUser(id: number) {
    for(let user of this.users) {
      if(user['id'] === id) {
        return user;
      }
    }
  }
}
