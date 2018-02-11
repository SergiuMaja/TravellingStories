import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { User } from "../../user.model";

@Component({
  selector: 'app-user-item',
  templateUrl: './user-item.component.html',
  styleUrls: ['./user-item.component.css']
})
export class UserItemComponent implements OnInit {

  @Input() user: User;
  @Input() id: number;

  constructor() { }

  ngOnInit() {
  }

}
