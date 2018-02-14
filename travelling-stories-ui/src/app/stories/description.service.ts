import { Description } from "./description.model";
/**
 * Created by Sergiu on 2/14/2018.
 */

export class DescriptionService {

  getDetailsByType(type: string, descriptions: Description[]) {
    for(let description of descriptions) {
      if(description.type === type) {
        return description.details;
      }
    }
    return '';
  }
}
