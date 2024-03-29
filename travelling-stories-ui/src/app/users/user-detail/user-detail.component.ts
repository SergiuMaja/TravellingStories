import { Component, OnInit } from '@angular/core';
import { User } from "../user.model";
import { UserService } from "../user.service";
import { ActivatedRoute, Params, Router } from "@angular/router";

@Component({
  selector: 'app-user-detail',
  templateUrl: './user-detail.component.html',
  styleUrls: ['./user-detail.component.css']
})
export class UserDetailComponent implements OnInit {
  user: User;
  id: number;

  constructor(private userService: UserService,
              private route: ActivatedRoute,
              private router: Router) { }

  ngOnInit() {
    this.route.params.subscribe(
      (params: Params) => {
        this.id = +params['id'];
        this.user = this.userService.getUser(this.id);
      }
    );
  }

  onEditUser() {
    this.router.navigate(['edit'], { relativeTo: this.route });
  }

  onDeleteUser() {
    this.userService.deleteUser(this.id);
    this.router.navigate(['/users']);
  }

}
