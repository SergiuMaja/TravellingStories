import { Description } from "./description.model";
/**
 * Created by Sergiu on 2/14/2018.
 */

export class DescriptionService {

  getDescriptionByType(type: string, descriptions: Description[]) {
    for(let description of descriptions) {
      if(description.type === type) {
        return description;
      }
    }
    return new Description('', '');
  }
}
