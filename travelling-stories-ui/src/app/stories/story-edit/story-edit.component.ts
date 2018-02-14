import { Component, OnInit, OnDestroy } from '@angular/core';
import { Destination } from "../destination.model";
import { ActivatedRoute, Params } from "@angular/router";
import { StoryService } from "../story.service";
import { FormGroup, FormControl, Validators } from "@angular/forms";
import { Story } from "../story.model";
import { User } from "../../users/user.model";
import { Subscription } from "rxjs/Subscription";
import { DescriptionService } from "../description.service";
import { DestinationService } from "../destination.service";

@Component({
  selector: 'app-story-edit',
  templateUrl: './story-edit.component.html',
  styleUrls: ['./story-edit.component.css']
})
export class StoryEditComponent implements OnInit, OnDestroy {
  storyId: number;
  editMode = false;
  storyForm: FormGroup;
  creator: User = new User();
  creatorChangedSubscription: Subscription;
  destinations: Destination[] = [];
  destinationsChangedSubscription: Subscription;
  lat: number = 46.7428338;
  lng: number = 23.5009179;
  locationPinned: boolean = false;

  constructor(private route: ActivatedRoute,
              private storyService: StoryService,
              private descriptionService: DescriptionService,
              private destinationService: DestinationService) { }

  ngOnInit() {
    this.creatorChangedSubscription = this.storyService.creatorChanged.subscribe(
      (creator: User) => {
        this.creator = creator;
      }
    );
    this.destinationsChangedSubscription = this.destinationService.destinationsChanged.subscribe(
      (destinations: Destination[]) => {
        this.destinations = destinations;
      }
    );
    this.route.params.subscribe(
      (params: Params) => {
        this.storyId = +params['id'];
        this.editMode = params['id'] != null;
        this.initForm();
      }
    );
    this.destinationService.getDestinations();
  }

  private initForm() {
    let formStory = new Story();
    let formDestination = new Destination();
    let formDescriptions = [];

    if(this.editMode) {
      formStory = this.storyService.getStory(this.storyId);
      formDestination = formStory.destination;
      formDescriptions = formStory.descriptions;
      this.storyService.getStoryCreator(formStory.creatorId);
    }

    this.storyForm = new FormGroup({
      'storyTitle': new FormControl(formStory.title, Validators.required),
      'creator': new FormControl({value: this.creator.screenName, disabled: true}),
      'createdDate': new FormControl({value: formStory.createdDate, disabled: true}),
      'updatedDate': new FormControl({value: formStory.updatedDate, disabled: true}),
      'destinationTitle': new FormControl(formDestination.title, Validators.required),
      'continent': new FormControl(formDestination.continent, Validators.required),
      'country': new FormControl(formDestination.country, Validators.required),
      'city': new FormControl(formDestination.city, Validators.required),
      'accommodation': new FormControl(this.descriptionService.getDetailsByType('ACCOMMODATION', formDescriptions)),
      'transportation': new FormControl(this.descriptionService.getDetailsByType('TRANSPORTATION', formDescriptions)),
      'culture': new FormControl(this.descriptionService.getDetailsByType('CULTURE', formDescriptions)),
      'food': new FormControl(this.descriptionService.getDetailsByType('FOOD', formDescriptions)),
      'expenses': new FormControl(this.descriptionService.getDetailsByType('EXPENSES', formDescriptions)),
      'attractions': new FormControl(this.descriptionService.getDetailsByType('ATTRACTIONS', formDescriptions))
    });
    if(formDestination.latitude != null && formDestination.longitude != null) {
      this.lat = +formDestination.latitude;
      this.lng = +formDestination.longitude;
      this.locationPinned = true;
    }
  }

  onSelectDestination(event) {
    const destTitleSelected = event.target.textContent.trim();
    this.storyForm.controls['destinationTitle'].setValue(destTitleSelected);
  }

  onLocationPin(event) {
    this.lat = event.coords.lat;
    this.lng = event.coords.lng;
    this.locationPinned = true;
  }

  ngOnDestroy() {
    this.creatorChangedSubscription.unsubscribe();
    this.destinationsChangedSubscription.unsubscribe();
  }

}
