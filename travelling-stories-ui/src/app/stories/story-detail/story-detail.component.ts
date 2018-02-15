import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Params } from "@angular/router";
import { Story } from "../story.model";
import { StoryService } from "../story.service";
import { User } from "../../users/user.model";
import { Subscription } from "rxjs/Subscription";
import { DescriptionService } from "../description.service";
import { DestinationService } from "../destination.service";

@Component({
  selector: 'app-story-detail',
  templateUrl: './story-detail.component.html',
  styleUrls: ['./story-detail.component.css']
})
export class StoryDetailComponent implements OnInit, OnDestroy {

  storyId: number;
  story: Story;
  creator: User = new User();
  creatorChangedSubscription: Subscription;
  lat: number = 46.7428338;
  lng: number = 23.5009179;
  locationPinned: boolean = false;

  constructor(private route: ActivatedRoute,
              private storyService: StoryService,
              private descriptionService: DescriptionService,
              private destinationService: DestinationService) {}

  ngOnInit() {
    this.creatorChangedSubscription = this.storyService.creatorChanged.subscribe(
      (creator: User) => {
        this.creator = creator;
      }
    );
    this.route.params.subscribe(
      (params: Params) => {
        this.storyId = +params['id'];
        this.story = this.storyService.getStory(this.storyId);
        this.storyService.getStoryCreator(this.story.creatorId);
        this.lat = +this.story.destination.latitude;
        this.lng = +this.story.destination.longitude;
        this.locationPinned = this.lat != null && this.lng != null;
      }
    );
  }

  getDescriptionDetailsByType(type: string) {
    return this.descriptionService.getDescriptionByType(type, this.story.descriptions).details;
  }

  onAddToFavorites() {
    this.destinationService.addDestinationToFavorites(this.story.destination.id);
  }

  ngOnDestroy() {
    this.creatorChangedSubscription.unsubscribe();
  }

}
