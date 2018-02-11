import { Component, OnInit } from '@angular/core';
import { User } from "../user.model";
import { UserService } from "../user.service";
import { Response } from "@angular/http";

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements OnInit {

  users: User[] = [];

  constructor(private userService: UserService) { }

  ngOnInit() {
    this.setupInitialUserList();
  }

  setupInitialUserList() {
    this.userService.getUsersFromServer().subscribe(
      (response: Response) => {
        const retrievedUsers = response.json();
        this.users = retrievedUsers;
        this.userService.setUsers(retrievedUsers);
      }
    );
  }

}
