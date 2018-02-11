import { Routes, RouterModule } from "@angular/router";
import { NgModule } from "@angular/core";

import { SignupComponent } from "./auth/signup/signup.component";
import { SigninComponent } from "./auth/signin/signin.component";
import { UsersComponent } from "./users/users.component";

const appRoutes: Routes = [
  { path: '', redirectTo: '/signin', pathMatch: 'full'},
  { path: 'users', component: UsersComponent },
  { path: 'signin', component: SigninComponent },
  { path: 'signup', component: SignupComponent }
];

@NgModule({
  imports: [ RouterModule.forRoot(appRoutes) ],
  exports: [ RouterModule ]
})
export class AppRoutingModule {
}
