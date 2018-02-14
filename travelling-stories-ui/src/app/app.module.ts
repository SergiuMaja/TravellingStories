import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { AppRoutingModule } from "./app-routing.module";
import { HttpModule } from "@angular/http";
import {
  MatCheckboxModule, MatFormFieldModule, MatInputModule, MatPaginatorModule,
  MatSortModule, MatOptionModule, MatSelectModule
} from "@angular/material";
import { MatTableModule } from "@angular/material/table";
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { AgmCoreModule } from "@agm/core";

import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { SigninComponent } from './auth/signin/signin.component';
import { SignupComponent } from './auth/signup/signup.component';
import { UsersComponent } from './users/users.component';
import { UserListComponent } from './users/user-list/user-list.component';
import { UserItemComponent } from './users/user-list/user-item/user-item.component';
import { UserDetailComponent } from './users/user-detail/user-detail.component';
import { UserStartComponent } from './users/user-start/user-start.component';
import { UserEditComponent } from './users/user-edit/user-edit.component';
import { StoriesComponent } from './stories/stories.component';
import { StoryDetailComponent } from './stories/story-detail/story-detail.component';
import { StoryEditComponent } from './stories/story-edit/story-edit.component';
import { StoryListComponent } from './stories/story-list/story-list.component';
import { DropdownDirective } from "./shared/dropdown.directive";

import { AuthService } from "./auth/auth.service";
import { UserService } from "./users/user.service";
import { AuthGuard } from "./auth/auth-guard.service";
import { StoryService } from "./stories/story.service";
import { DescriptionService } from "./stories/description.service";

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    SigninComponent,
    SignupComponent,
    UsersComponent,
    UserListComponent,
    UserItemComponent,
    UserDetailComponent,
    UserStartComponent,
    DropdownDirective,
    UserEditComponent,
    StoriesComponent,
    StoryDetailComponent,
    StoryEditComponent,
    StoryListComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    AppRoutingModule,
    MatCheckboxModule,
    MatTableModule,
    MatFormFieldModule,
    MatInputModule,
    MatPaginatorModule,
    MatSortModule,
    MatOptionModule,
    MatSelectModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    AgmCoreModule.forRoot({
      apiKey: 'AIzaSyAQZxPPsIvhZ3JWYk8F5ykvVuAGHIHFDDQ'
    })
  ],
  providers: [AuthService, UserService, AuthGuard, StoryService, DescriptionService],
  bootstrap: [AppComponent]
})
export class AppModule { }
