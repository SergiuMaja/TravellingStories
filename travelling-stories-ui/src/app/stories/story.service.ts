import { Http, Response } from "@angular/http";
import { Injectable } from "@angular/core";
import { Story } from "./story.model";
import { Subject } from "rxjs";
import { User } from "../users/user.model";

@Injectable()
export class StoryService {
  storiesChanged = new Subject<Story[]>();
  creatorChanged = new Subject<User>();
  private stories: Story[] = [];
  private creator: User = new User();

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

  getStory(id: number) {
    for (let story of this.stories) {
      if (story['id'] === id) {
        return story;
      }
    }
  }

  setStoryCreator(storyCreator: User) {
    this.creator = storyCreator;
    this.creatorChanged.next(this.creator);
  }

  getStoryCreator(userId: number) {
    return this.http.get('http://localhost:8080/user/' + userId).subscribe(
      (response: Response) => {
        this.setStoryCreator(response.json());
      }
    );
  }

  addStory(story: Story) {
    this.http.post('http://localhost:8080/story/save', story).subscribe(
      (response: Response) => {
        this.stories.push(response.json());
        this.storiesChanged.next(this.stories.slice());
      }
    );
  }

  updateStory(id: number, newStory: Story) {
    newStory.id = id;
    this.http.post('http://localhost:8080/story/save', newStory).subscribe(
      (response: Response) => {
        for (let story of this.stories) {
          if(story.id === id) {
            story = response.json();
            this.storiesChanged.next(this.stories.slice());
          }
        }
      }
    );
  }

}
