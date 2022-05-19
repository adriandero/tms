import {Role} from "./Roles";
//import {Inject, Injectable} from "@angular/core";


export type User = {
  id: number
  mail: string,
  img: string | undefined,
  firstname: string,
  lastname: string,
  roles: Role[],
  latestRefreshToken: string
}

