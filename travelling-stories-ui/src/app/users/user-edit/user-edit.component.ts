import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params } from "@angular/router";
import { FormGroup, FormControl, Validators } from "@angular/forms";
import { UserService } from "../user.service";

@Component({
  selector: 'app-user-edit',
  templateUrl: './user-edit.component.html',
  styleUrls: ['./user-edit.component.css']
})
export class UserEditComponent implements OnInit {
  id: number;
  userForm: FormGroup;

  constructor(private route: ActivatedRoute,
              private userService: UserService) { }

  ngOnInit() {
    this.route.params.subscribe(
      (params: Params) => {
        this.id = +params['id'];
        this.initForm();
      }
    );
  }

  private initForm() {
    let screenName = '';
    let email = '';
    let password = '';
    let receiveEmail = false;

    if(this.id != null) {
      const user = this.userService.getUser(this.id);
      screenName = user.screenName;
      email = user.email;
      password = user.password;
      receiveEmail = user.receiveEmail;
    }

    this.userForm = new FormGroup({
      'screenName': new FormControl(screenName, Validators.required),
      'email': new FormControl(email, Validators.required),
      'password': new FormControl(password, Validators.required),
      'receiveEmail': new FormControl(receiveEmail, Validators.required)
    });
  }

  onSubmit() {
    console.log(this.userForm);
  }

}
