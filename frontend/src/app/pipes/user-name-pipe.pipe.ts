import {Pipe, PipeTransform} from '@angular/core';

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
