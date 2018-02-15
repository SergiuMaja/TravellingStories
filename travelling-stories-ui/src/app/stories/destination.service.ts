import { Injectable } from "@angular/core";
import { Subject } from "rxjs/Subject";
import { Destination } from "./destination.model";
import { Http, Response } from "@angular/http";
import {AuthService} from "../auth/auth.service";

@Injectable()
export class DestinationService {
  destinationsChanged = new Subject<Destination[]>();
  private destinations: Destination[] = [];

  constructor(private http: Http,
              private authService: AuthService) {}

  getDestinations() {
    this.http.get('http://localhost:8080/destination/all').subscribe(
      (response: Response) => {
        this.setDestinations(response.json());
      }
    );
  }

  addDestinationToFavorites(destinationId: number) {
    let body = { 'userId': this.authService.authenticatedUser.id };
    this.http.post('http://localhost:8080/destination/' + destinationId + '/favorite', body)
      .subscribe(
        (response: Response) => {
          if(response.status === 200) {
            console.log("Destination was added to user's favorites");
          }
        }
      );
  }

  setDestinations(destinations: Destination[]) {
    this.destinations = destinations;
    this.destinationsChanged.next(this.destinations.slice());
  }
}
