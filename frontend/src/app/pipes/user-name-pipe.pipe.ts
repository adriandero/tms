import {Pipe, PipeTransform} from '@angular/core';
import {User} from "../model/User";

@Pipe({
  name: 'userName'
})
export class UserNamePipe implements PipeTransform {

  transform(value: string, ...args: unknown[]): String {
    return `${capitalize(value.toLowerCase())} \n`
  }

}

const capitalize = (s: string) => {
  return s.charAt(0).toUpperCase() + s.slice(1)
}
