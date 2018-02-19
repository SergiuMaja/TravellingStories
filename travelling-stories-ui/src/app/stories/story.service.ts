import { Http, Response } from "@angular/http";
import { Injectable } from "@angular/core";
import { Story } from "./story.model";
import { Subject } from "rxjs";
import { User } from "../users/user.model";
import { Globals } from "../shared/globals.service";

@Injectable()
export class StoryService {
  storiesChanged = new Subject<Story[]>();
  creatorChanged = new Subject<User>();
  private stories: Story[] = [];
  private creator: User = new User();

  constructor(private http: Http,
              private globals: Globals) {}

  getStories() {
    this.http.get('http://'+ this.globals.host +'/story/all').subscribe(
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
    return this.http.get('http://'+ this.globals.host +'/user/' + userId).subscribe(
      (response: Response) => {
        this.setStoryCreator(response.json());
      }
    );
  }

  addStory(story: Story) {
    this.http.post('http://'+ this.globals.host +'/story/save', story).subscribe(
      (response: Response) => {
        this.stories.push(response.json());
        this.storiesChanged.next(this.stories.slice());
      }
    );
  }

  updateStory(id: number, newStory: Story) {
    newStory.id = id;
    this.http.post('http://'+ this.globals.host +'/story/save', newStory).subscribe(
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

  deleteStory(id: number) {
    this.stories.forEach((story, index) => {
      if(story['id'] === id) {
        this.stories.splice(index, 1);
      }
    });
    //DELETING THE STORY FROM THE SERVER
    this.http.delete('http://'+ this.globals.host +'/story/delete/' + id).subscribe(
      (response: Response) => {
        if(response.status === 200) {
          this.storiesChanged.next(this.stories.slice());
        }
      },
      (error) => console.log(error)
    );
  }

}
