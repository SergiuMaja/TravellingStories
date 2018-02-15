import { Component, OnInit, OnDestroy, ViewChild, AfterViewInit } from '@angular/core';
import { MatTableDataSource, MatPaginator, MatSort } from "@angular/material";
import { StoryService } from "../story.service";
import { Story } from "../story.model";
import { Subscription } from "rxjs/Subscription";
import { Router, ActivatedRoute } from "@angular/router";
import {AuthService} from "../../auth/auth.service";

@Component({
  selector: 'app-story-list',
  templateUrl: './story-list.component.html',
  styleUrls: ['./story-list.component.css']
})
export class StoryListComponent implements OnInit, OnDestroy, AfterViewInit {
  dataSource: MatTableDataSource<StoryData>;
  displayedColumns = ['title', 'destination', 'createdDate', 'rating', 'ratesNr', 'view'];
  stories: Story[] = [];
  subscription: Subscription;

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor(private storyService: StoryService,
              private router: Router,
              private route: ActivatedRoute,
              private authService: AuthService) {
    const storyRows = createStoryRows(this.stories);
    this.dataSource = new MatTableDataSource(storyRows);
    if(this.authService.isAdminAuthenticated()) {
      this.displayedColumns.push('edit');
      this.displayedColumns.push('delete');
    }
  }

  ngOnInit() {
    this.subscription = this.storyService.storiesChanged.subscribe(
      (stories: Story[]) => {
        this.stories = stories;
        this.dataSource.data = createStoryRows(stories);
      }
    );
    this.storyService.getStories();
  }

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  applyFilter(filterValue: string) {
    filterValue = filterValue.trim(); //Remove whitespace
    filterValue = filterValue.toLowerCase(); //Datasource defaults to lowercase matches
    this.dataSource.filter = filterValue;
  }

  onAddNewStory() {
    this.router.navigate(['new'], { relativeTo: this.route });
  }

  onViewStory(storyId: number) {
    this.router.navigate([storyId], { relativeTo: this.route });
  }

  onEditStory(storyId: number) {
    this.router.navigate([storyId, 'edit'], { relativeTo: this.route });
  }

  onDeleteStory(storyId: number) {
    this.storyService.deleteStory(storyId);
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }

}

function createStoryRows(stories: Story[]): StoryData[] {
  let storyRows: StoryData[] = [];

  for(let story of stories) {
    let row = {
      id: story.id,
      title: story.title,
      destination: story.destination.title,
      createdDate: story.createdDate,
      rating: story.rating,
      ratesNr: story.ratesNumber
    };
    storyRows.push(row);
  }
  return storyRows;
}

export interface StoryData {
  id: number;
  title: string;
  destination: string;
  createdDate: string;
  rating: number;
  ratesNr: number;
}
