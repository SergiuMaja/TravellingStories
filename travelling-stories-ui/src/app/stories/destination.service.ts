import { Injectable } from "@angular/core";
import { Subject } from "rxjs/Subject";
import { Destination } from "./destination.model";
import { Http, Response } from "@angular/http";

@Injectable()
export class DestinationService {
  destinationsChanged = new Subject<Destination[]>();
  private destinations: Destination[] = [];

  constructor(private http: Http) {}

  getDestinations() {
    this.http.get('http://localhost:8080/destination/all').subscribe(
      (response: Response) => {
        this.setDestinations(response.json());
      }
    );
  }

  setDestinations(destinations: Destination[]) {
    this.destinations = destinations;
    this.destinationsChanged.next(this.destinations.slice());
  }
}
