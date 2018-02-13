import { User } from "./user.model";
import { Injectable } from "@angular/core";
import { Http, Response } from "@angular/http";
import { Subject } from "rxjs";
import { FormGroup } from "@angular/forms";

@Injectable()
export class UserService {
  usersChanged = new Subject<User[]>();
  private users: User[] = [];

  constructor(private http: Http) {}

  getUsers() {
    this.http.get('http://localhost:8080/user/all').subscribe(
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

  updateUser(id: number, userForm: FormGroup) {
    const formValue = userForm.value;
    let updatedUser: User;

    for(let user of this.users) {
      if(user['id'] === id) {
        user.screenName = formValue['screenName'];
        user.email = formValue['email'];
        user.password = formValue['password'];
        user.receiveEmail = formValue['receiveEmail'];
        updatedUser = user;
      }
    }
    this.usersChanged.next(this.users.slice());
    //SAVING THE USER ON THE SERVER
    this.http.post('http://localhost:8080/user/save', updatedUser).subscribe(
      (response) => {
        console.log(response);
      },
      (error) => console.log(error)
    );
  }

  deleteUser(id: number) {
    this.users.forEach((user, index) => {
      if(user['id'] === id) {
        this.users.splice(index,1);

      }
    });
    //DELETING THE USER FROM THE SERVER
    this.http.delete('http://localhost:8080/user/delete/' + id).subscribe(
      (response) => {
        if(response.status === 200) {
          this.usersChanged.next(this.users.slice());
        }
      },
      (error) => console.log(error)
    );
  }
}
