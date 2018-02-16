import { Component, OnInit } from '@angular/core';
import { NgForm } from "@angular/forms";
import { AuthService } from "../auth.service";
import { Router } from "@angular/router";

@Component({
  selector: 'app-signin',
  templateUrl: './signin.component.html',
  styleUrls: ['./signin.component.css']
})
export class SigninComponent implements OnInit {

  invalidCredentials: boolean = false;

  constructor(private authService: AuthService,
              private router: Router) { }

  ngOnInit() {
  }

  onSignin(form: NgForm) {
    const screenName = form.value.screenName;
    const password = form.value.password;

    this.authService.signinUser(screenName, password).subscribe(
      (response) => {
        if(response.status === 200) {
          this.authService.authenticatedUser = response.json();
          this.router.navigate(['/']);
        } else {
          this.invalidCredentials = true;
        }
      },
      (error) => console.log(error)
    );
  }

}
