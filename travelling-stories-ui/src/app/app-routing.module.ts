import { Routes, RouterModule } from "@angular/router";
import { NgModule } from "@angular/core";
import { AuthGuard } from "./auth/auth-guard.service";

import { SignupComponent } from "./auth/signup/signup.component";
import { SigninComponent } from "./auth/signin/signin.component";
import { UsersComponent } from "./users/users.component";
import { UserStartComponent } from "./users/user-start/user-start.component";
import { UserDetailComponent } from "./users/user-detail/user-detail.component";
import { UserEditComponent } from "./users/user-edit/user-edit.component";
import { StoriesComponent } from "./stories/stories.component";
import { StoryListComponent } from "./stories/story-list/story-list.component";
import { StoryDetailComponent } from "./stories/story-detail/story-detail.component";
import { StoryEditComponent } from "./stories/story-edit/story-edit.component";

const appRoutes: Routes = [
  { path: '', redirectTo: '/stories', pathMatch: 'full' },
  { path: 'stories', component: StoriesComponent, canActivate: [AuthGuard],
    children:
      [
        { path: '', component: StoryListComponent },
        { path: ':id', component: StoryDetailComponent },
        { path: ':id/edit', component: StoryEditComponent }
      ]
  },
  { path: 'users', component: UsersComponent,
    children:
      [
        { path: '', component: UserStartComponent },
        { path: ':id', component: UserDetailComponent },
        { path: ':id/edit', component: UserEditComponent }
      ]
  },
  { path: 'signin', component: SigninComponent },
  { path: 'signup', component: SignupComponent }
];

@NgModule({
  imports: [ RouterModule.forRoot(appRoutes) ],
  exports: [ RouterModule ]
})
export class AppRoutingModule {
}
