import { Component, OnInit, OnDestroy } from '@angular/core';
import { MatTableDataSource } from "@angular/material";
import { StoryService } from "../story.service";
import { Story } from "../story.model";
import { Subscription } from "rxjs/Subscription";

@Component({
  selector: 'app-story-list',
  templateUrl: './story-list.component.html',
  styleUrls: ['./story-list.component.css']
})
export class StoryListComponent implements OnInit, OnDestroy {
  dataSource: MatTableDataSource<StoryData>;
  displayedColumns = ['title', 'destination', 'createdDate', 'rating', 'ratesNr', 'view', 'edit', 'delete'];
  stories: Story[];
  subscription: Subscription;

  constructor(private storyService: StoryService) { }

  ngOnInit() {
    this.subscription = this.storyService.storiesChanged.subscribe(
      (stories: Story[]) => {
        this.stories = stories;
      }
    );
    this.storyService.getStories();
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }

}

export interface StoryData {
  title: string;
  destination: string;
  createdDate: string;
  rating: number;
  ratesNr: number;
}
