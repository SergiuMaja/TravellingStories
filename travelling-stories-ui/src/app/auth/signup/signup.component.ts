import { Component, OnInit } from '@angular/core';
import { NgForm } from "@angular/forms";
import { AuthService } from "../auth.service";
import { User } from "../../users/user.model";
import { Router } from "@angular/router";

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {

  constructor(private authService: AuthService,
              private router: Router) { }

  ngOnInit() {
  }

  onSignup(form: NgForm) {
    const screenName = form.value.screenName;
    const email = form.value.email;
    const password = form.value.password;

    const user = new User();
    user.setScreenName(screenName);
    user.setEmail(email);
    user.setPassword(password);

    this.authService.signupUser(user).subscribe(
      (response) => {
        console.log(response);
        this.router.navigate(['/']);
      },
      (error) => console.log(error)
    );
  }
}
