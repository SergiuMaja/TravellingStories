import { Destination } from "./destination.model";
import { Description } from "./description.model";

export class Story {
  public id: number;
  public creatorId: number;
  public destination: Destination;
  public title: string;
  public createdDate: string;
  public updatedDate: string;
  public rating: number;
  public ratesNumber: number;
  public resources: string;
  public descriptions: Description[];

  constructor() {}
}
