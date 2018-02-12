import { Component, OnInit } from '@angular/core';
import { MatTableDataSource } from "@angular/material";

@Component({
  selector: 'app-story-list',
  templateUrl: './story-list.component.html',
  styleUrls: ['./story-list.component.css']
})
export class StoryListComponent implements OnInit {
  dataSource: MatTableDataSource<StoryData>;
  displayedColumns = ['title', 'edit'];

  constructor() { }

  ngOnInit() {
  }

}

export interface StoryData {
  title: string;
}
