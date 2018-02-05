import { Routes, RouterModule } from "@angular/router";
import { NgModule } from "@angular/core";

import {SignupComponent} from "./auth/signup/signup.component";

const appRoutes: Routes = [
  { path: 'signup', component: SignupComponent }
];

@NgModule({
  imports: [ RouterModule.forRoot(appRoutes) ],
  exports: [ RouterModule ]
})
export class AppRoutingModule {
}
