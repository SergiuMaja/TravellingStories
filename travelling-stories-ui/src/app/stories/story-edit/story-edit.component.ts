import { Component, OnInit } from '@angular/core';
import {Destination} from "../destination.model";

@Component({
  selector: 'app-story-edit',
  templateUrl: './story-edit.component.html',
  styleUrls: ['./story-edit.component.css']
})
export class StoryEditComponent implements OnInit {

  editMode = false;
  lat: number = 46.7428338;
  lng: number = 23.5009179;
  destinationSelected: boolean = false;
  destTitleSelected: string;
  destinations: Destination[] = [
    {
      "id": 2,
      "continent": "Europe",
      "country": "Romania",
      "city": "Bucharest",
      "title": "Bucharest Old Town",
      "latitude": null,
      "longitude": null
    },
    {
      "id": 3,
      "continent": "Europe",
      "country": "Romania",
      "city": "Brasov",
      "title": "Peles Castle",
      "latitude": null,
      "longitude": null
    }
  ];

  constructor() { }

  ngOnInit() {
  }

  onSelectDestination(event) {
    this.lat = event.coords.lat;
    this.lng = event.coords.lng;
    this.destinationSelected = true;
  }

}
