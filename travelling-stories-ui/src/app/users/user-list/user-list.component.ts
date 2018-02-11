import { Component, OnInit, OnDestroy } from '@angular/core';
import { User } from "../user.model";
import { UserService } from "../user.service";
import { Subscription } from "rxjs";

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements OnInit, OnDestroy {
  users: User[];
  subscription: Subscription;

  constructor(private userService: UserService) { }

  ngOnInit() {
    this.subscription = this.userService.usersChanged.subscribe(
      (users: User[]) => {
        this.users = users;
      }
    );
    this.userService.getUsers();
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }

}
