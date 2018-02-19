import { Injectable } from "@angular/core";
import { Subject } from "rxjs/Subject";
import { Destination } from "./destination.model";
import { Http, Response } from "@angular/http";
import { AuthService } from "../auth/auth.service";
import { Globals } from "../shared/globals.service";

@Injectable()
export class DestinationService {
  destinationsChanged = new Subject<Destination[]>();
  private destinations: Destination[] = [];

  constructor(private http: Http,
              private authService: AuthService,
              private globals: Globals) {}

  getDestinations() {
    this.http.get('http://'+ this.globals.host +'/destination/all').subscribe(
      (response: Response) => {
        this.setDestinations(response.json());
      }
    );
  }

  addDestinationToFavorites(destinationId: number) {
    let body = { 'userId': this.authService.authenticatedUser.id };
    this.http.post('http://'+ this.globals.host +'/destination/' + destinationId + '/favorite', body)
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
