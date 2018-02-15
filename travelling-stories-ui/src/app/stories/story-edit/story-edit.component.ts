import { Component, OnInit, OnDestroy } from '@angular/core';
import { Destination } from "../destination.model";
import { ActivatedRoute, Params, Router } from "@angular/router";
import { StoryService } from "../story.service";
import { FormGroup, FormControl, Validators } from "@angular/forms";
import { Story } from "../story.model";
import { User } from "../../users/user.model";
import { Subscription } from "rxjs/Subscription";
import { DescriptionService } from "../description.service";
import { DestinationService } from "../destination.service";
import { AuthService } from "../../auth/auth.service";
import { Description } from "../description.model";

@Component({
  selector: 'app-story-edit',
  templateUrl: './story-edit.component.html',
  styleUrls: ['./story-edit.component.css']
})
export class StoryEditComponent implements OnInit, OnDestroy {
  storyId: number;
  editMode = false;
  storyForm: FormGroup;
  destinationForm: FormGroup;
  creator: User = new User();   //story creator
  creatorChangedSubscription: Subscription;   //used to trigger when the creator was fetched from server
  destinations: Destination[] = [];   //these are all destinations, added in the dropdown
  destinationsChangedSubscription: Subscription;  //used to trigger when the destinations were fetched from server
  selectedDestId: number;   //the value selected in the dropdown
  storyDestId: number; //used to store the id of the destination if the story is edited
  lat: number = 46.7428338;
  lng: number = 23.5009179;
  locationPinned: boolean = false;
  storyDescriptions = [];
  descriptionTypes = ['ACCOMMODATION','TRANSPORTATION','CULTURE','FOOD','EXPENSES','ATTRACTIONS'];

  constructor(private route: ActivatedRoute,
              private storyService: StoryService,
              private descriptionService: DescriptionService,
              private destinationService: DestinationService,
              private authService: AuthService,
              private router: Router) { }

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
    let story = new Story();
    let storyDestination = new Destination();

    if(this.editMode) {
      story = this.storyService.getStory(this.storyId);
      storyDestination = story.destination;
      this.storyDescriptions = story.descriptions;
      this.storyService.getStoryCreator(story.creatorId);
      this.storyDestId = storyDestination.id;
    }

    this.destinationForm = new FormGroup({
      'title': new FormControl(storyDestination.title, Validators.required),
      'continent': new FormControl(storyDestination.continent, Validators.required),
      'country': new FormControl(storyDestination.country, Validators.required),
      'city': new FormControl(storyDestination.city, Validators.required)
    });

    this.storyForm = new FormGroup({
      'title': new FormControl(story.title, Validators.required),
      'creator': new FormControl({value: this.creator.screenName, disabled: true}),
      'createdDate': new FormControl({value: story.createdDate, disabled: true}),
      'updatedDate': new FormControl({value: story.updatedDate, disabled: true}),
      'destination': this.destinationForm,
      'ACCOMMODATION': new FormControl(
        this.descriptionService.getDescriptionByType('ACCOMMODATION', this.storyDescriptions).details
      ),
      'TRANSPORTATION': new FormControl(
        this.descriptionService.getDescriptionByType('TRANSPORTATION', this.storyDescriptions).details
      ),
      'CULTURE': new FormControl(
        this.descriptionService.getDescriptionByType('CULTURE', this.storyDescriptions).details
      ),
      'FOOD': new FormControl(
        this.descriptionService.getDescriptionByType('FOOD', this.storyDescriptions).details
      ),
      'EXPENSES': new FormControl(
        this.descriptionService.getDescriptionByType('EXPENSES', this.storyDescriptions).details
      ),
      'ATTRACTIONS': new FormControl(
        this.descriptionService.getDescriptionByType('ATTRACTIONS', this.storyDescriptions).details
      )
    });
    if(storyDestination.latitude != null && storyDestination.longitude != null) {
      this.lat = +storyDestination.latitude;
      this.lng = +storyDestination.longitude;
      this.locationPinned = true;
    }
  }

  onSelectDestination(event) {
    const destTitleSelected = event.target.textContent.trim();
    this.destinationForm.controls['title'].setValue(destTitleSelected);
    this.storyDestId = this.selectedDestId;
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

  onSubmit() {
    let savedStory = new Story();

    savedStory.destination = this.storyForm.controls['destination'].value;
    savedStory.destination.id = this.storyDestId;
    if(this.locationPinned) {
      savedStory.destination.latitude = '' + this.lat;
      savedStory.destination.longitude = '' + this.lng;
    }

    savedStory.title = this.storyForm.controls['title'].value;
    savedStory.createdDate = this.storyForm.controls['createdDate'].value;

    let descriptions = [];
    for (let type of this.descriptionTypes) {
      let descriptionDetails = this.storyForm.controls[type].value;
      let description = this.descriptionService.getDescriptionByType(type, this.storyDescriptions);
      if(description.id != null) { //this means, this description was already added on the story, but might have changed
        description.details = descriptionDetails;
      } else {
        description = new Description(type, descriptionDetails);
      }
      descriptions.push(description);
    }
    savedStory.descriptions = descriptions;

    if(this.editMode) {
      savedStory.creatorId = this.creator.id;
      this.storyService.updateStory(this.storyId, savedStory);
    } else {
      savedStory.creatorId = this.authService.authenticatedUser.id;
      this.storyService.addStory(savedStory);
    }
    console.log(savedStory);
  }

  onCancel() {
    this.router.navigate(['/stories']);
  }

}
