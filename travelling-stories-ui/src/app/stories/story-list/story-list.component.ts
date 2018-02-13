import {Component, OnInit, OnDestroy, ViewChild, AfterViewInit, AfterContentInit} from '@angular/core';
import { MatTableDataSource, MatPaginator, MatSort } from "@angular/material";
import { StoryService } from "../story.service";
import { Story } from "../story.model";
import { Subscription } from "rxjs/Subscription";

@Component({
  selector: 'app-story-list',
  templateUrl: './story-list.component.html',
  styleUrls: ['./story-list.component.css']
})
export class StoryListComponent implements OnInit, OnDestroy, AfterViewInit {
  dataSource: MatTableDataSource<StoryData>;
  displayedColumns = ['title', 'destination', 'createdDate', 'rating', 'ratesNr', 'view', 'edit', 'delete'];
  stories: Story[] = [];
  subscription: Subscription;

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor(private storyService: StoryService) {
    const storyRows = createStoryRows(this.stories);
    this.dataSource = new MatTableDataSource(storyRows);
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

  onViewStory(storyId: number) {
    console.log(storyId);
  }

  onEditStory(storyId: number) {
    console.log(storyId);
  }

  onDeleteStory(storyId: number) {
    console.log(storyId);
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
    }
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
