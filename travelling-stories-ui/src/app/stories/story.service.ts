import { Http, Response } from "@angular/http";
import { Injectable } from "@angular/core";
import { Story } from "./story.model";
import { Subject } from "rxjs";

@Injectable()
export class StoryService {
  storiesChanged = new Subject<Story[]>();
  private stories: Story[] = [];

  constructor(private http: Http) {}

  getStories() {
    this.http.get('http://localhost:8080/story/all').subscribe(
      (response: Response) => {
        this.setStories(response.json());
      }
    );
  }

  setStories(stories: Story[]) {
    this.stories = stories;
    this.storiesChanged.next(this.stories.slice());
  }
}
