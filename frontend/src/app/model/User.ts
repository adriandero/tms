import { Group } from "./Tender";
//import {Inject, Injectable} from "@angular/core";

export interface User {
  id : number,
  mail : string
  username :string,
  firstName : string,
  lastName: string,
  password: string,
  group : Group,
  enabled :boolean,
  accountNonLocked:boolean,
  authorities: string[],
  credentialsNonExpired :boolean,
  accountNonExpired :boolean,
}
